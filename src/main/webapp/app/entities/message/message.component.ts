import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from '../community/community.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from '../profile/profile.service';
import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from './message.service';

import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';

@Component({
    selector: 'jhi-message',
    templateUrl: './message.component.html'
})
export class MessageComponent implements OnInit, OnDestroy {
    currentAccount: any;
    messages: IMessage[];
    communities: ICommunity[];
    profiles: IProfile[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    paramMessageProfileId: any;
    owner: any;
    isAdmin: boolean;
    arrayAux = [];
    arrayIds = [];

    constructor(
        private messageService: MessageService,
        private communityService: CommunityService,
        private profileService: ProfileService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.activatedRoute.queryParams.subscribe( params => {
            this.paramMessageProfileId = params.profileIdEquals;
        });
    }

    loadAll() {
        const query = {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            };
        if ( this.paramMessageProfileId  != null) {
            query['profileId.equals'] = this.paramMessageProfileId;
        }
        this.messageService
            .query(query)
            .subscribe(
                (res: HttpResponse<IMessage[]>) => this.paginateMessages(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/message'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/message',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.owner = account.id;
//            console.log('CONSOLOG: M:ngOnInit & O: this.currentAccount : ',  this.currentAccount);
//            console.log('CONSOLOG: M:ngOnInit & O: this.owner : ',  this.owner);
            this.myMessagesCommunities();
//            this.principal.hasAnyAuthority(['ROLE_ADMIN']).then( result => {
//                this.isAdmin = result;
//                if ( this.isAdmin === true ) {
//                    this.loadAll();
//                } else {
//                    this.myMessagesCommunities();
//                }
//            });
        });
        this.registerChangeInMessages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMessage) {
        return item.id;
    }

    registerChangeInMessages() {
        this.eventSubscriber = this.eventManager.subscribe('messageListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    myMessagesCommunities() {
        console.log('CONSOLOG: M:myMessagesCommunities & O: this.currentAccount : ',  this.currentAccount);
        const query = {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.communityService
            .query(query)
            .subscribe(
                (res: HttpResponse<ICommunity[]>) => {
                    this.communities = res.body;
                    // ADEMAS de las que es dueño necesitamos las COM que el tipo está siguiendo.
                    console.log('CONSOLOG: M:myMessagesCommunities & O: this.communities : ',  this.communities);
                    this.communitiesMessages();
                    },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.myMessagesProfiles();
    }

    private myMessagesProfiles() {
        const query = {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.profileService
            .query(query)
            .subscribe(
                    (res: HttpResponse<IProfile[]>) => {
                        this.profiles = res.body;
                        console.log('CONSOLOG: M:myMessagesCommunities & O: this.profiles : ',  this.profiles);
                        this.profilesMessages();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private communitiesMessages() {
        const query = {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            };
        if ( this.communities  != null) {
            const arrayCommmunities = [];
            this.communities.forEach(community => {
                arrayCommmunities.push(community.id);
            });
            query['communityId.in'] = arrayCommmunities;
        }
        this.messageService
            .query(query)
            .subscribe(
                    (res: HttpResponse<IMessage[]>) => {
                        this.messages = res.body;
                        console.log('CONSOLOG: M:communitiesMessages & O: this.messages : ',  this.messages);
                        this.myMessagesProfiles(); // ????????????????????????????????????????????????????????????????????
                     },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private profilesMessages() {
        const query = {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            };
        if ( this.profiles  != null) {
            const arrayProfiles = [];
            this.profiles.forEach(profile => {
                arrayProfiles.push(profile.id);
            });
            query['profileId.in'] = arrayProfiles;
        }
        this.messageService
            .query(query)
            .subscribe(
                    (res: HttpResponse<IMessage[]>) => {
//                        this.messages = this.messages.concat(res.body);
                        console.log('CONSOLOG: M:profilesMessages & O: this.messages1 : ',  this.messages);
                        this.messages = this.filterMessages(this.messages.concat(res.body));
                        console.log('CONSOLOG: M:profilesMessages & O: this.messages2 : ',  this.messages);
                        this.paginateMessages(this.messages, res.headers);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private filterMessages( messages ) {
        this.arrayAux = [];
        this.arrayIds = [];
        messages.map( x => {
            if ( this.arrayIds.length >= 1 && this.arrayIds.includes( x.id ) === false ) {
                this.arrayAux.push( x );
                this.arrayIds.push( x.id );
            } else if ( this.arrayIds.length === 0 ) {
                this.arrayAux.push( x );
                this.arrayIds.push( x.id );
            }
        } );
        console.log('CONSOLOG: M:filterMessages & O: this.messages : ', this.arrayIds, this.arrayAux );
        return this.arrayAux;
    }

    private paginateMessages(data: IMessage[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = data.length;
        this.queryCount = this.totalItems;
        this.messages = data;
        console.log('CONSOLOG: M:paginateFollows & O: this.messages : ',  this.messages);
        console.log('CONSOLOG: M:paginateFollows & O: this.owner : ',  this.owner);
        console.log('CONSOLOG: M:paginateFollows & O: this.isAdmin : ',  this.isAdmin);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
