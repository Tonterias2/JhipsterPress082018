import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IInterest } from 'app/shared/model/interest.model';
import { InterestService } from './interest.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from '../community/community.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from '../profile/profile.service';

import { ITEMS_PER_PAGE } from 'app/shared';
import { Principal } from 'app/core';
import { map } from 'rxjs/operators';

@Component({
    selector: 'jhi-interest',
    templateUrl: './interest.component.html'
})
export class InterestComponent implements OnInit, OnDestroy {
    currentAccount: any;
    interests: IInterest[];
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
    owner: any;
    isAdmin: boolean;

    constructor(
        private interestService: InterestService,
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
    }

    loadAll() {
        this.interestService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<IInterest[]>) => this.paginateInterests(res.body, res.headers),
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
        this.router.navigate(['/interest'], {
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
            '/interest',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.owner = account.id;
            this.principal.hasAnyAuthority(['ROLE_ADMIN']).then( result => {
                this.isAdmin = result;
            });
        });
        this.mymap(); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        this.registerChangeInInterests();
    }

    private myInterestsCommunities() {
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
                    this.communitiesInterests();
                    },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.myInterestsProfiles();
    }

    private myInterestsProfiles() {
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
                        this.myProfileInterests();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private communitiesInterests() {
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
        this.interestService
            .query(query)
            .subscribe(
                    (res: HttpResponse<IInterest[]>) => {
                        this.interests = res.body;
                        // !!!!!!! AQUI NECESITO UN MAP PARA METER LOS  interest de las COMMUNITIES
                        this.myInterestsProfiles(); // ????????????????????????????????????????????????????????????????????
                     },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private myProfileInterests() {
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
        this.interestService
            .query(query)
            .subscribe(
                    (res: HttpResponse<IInterest[]>) => {
                        // !!!!!!! AQUI NECESITO UN MAP PARA METER LOS RESTANTES interest de los PROFILES sin DUPLICAR
                        this.interests = this.interests.concat(res.body);
                        this.paginateInterests(this.interests, res.headers);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IInterest) {
        return item.id;
    }

    registerChangeInInterests() {
        this.eventSubscriber = this.eventManager.subscribe('interestListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateInterests(data: IInterest[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.interests = data;
        console.log('MESSAGES', this.interests);
        console.log('OWNER', this.owner);
        console.log('ISADMIN', this.isAdmin);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    // BORRRARRRRRRRRRRRRRR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private mymap() {
        console.log('ENTRO EN MAY MAP');
//        const arrayCommmunities = [];
//        plus5 = new map();
//        const query = {
//            };
//            query['userId.equals'] = 3;
//        this.communityService
//            .query(query)
//            .subscribe(
//                (res: HttpResponse<ICommunity[]>) => {
//                    this.communities = res.body;
//                    console.log( 'Array this.communities: ', this.communities );
//                    this.communities.forEach(community => {
//                        arrayCommmunities.push(community.id);
//                        const plus5 = this.communities.map(( val, i, arr ) => {
//                            console.log( 'PLUS5 map del arrayCommmunities', arrayCommmunities );
//                            console.log( 'PLUS5 map del array', plus5 );
//                            console.log( 'PLUS5 map del this.communities', this.communities );
//                        } );
//                    });
//                    },
//                (res: HttpErrorResponse) => this.onError(res.message)
//            );
//        let arr = [1, 2, 3, 4];
//        let newArr = arr.map(( val, i, arr ) => {
//            return {
//                value: val,
//                index: i
//            };
//        } );
    }
    // BORRRARRRRRRRRRRRRRR !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
}
