import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { INotification } from 'app/shared/model/notification.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { NotificationService } from './notification.service';

@Component({
    selector: 'jhi-notification',
    templateUrl: './notification.component.html'
})
export class NotificationComponent implements OnInit, OnDestroy {
    currentAccount: any;
    notifications: INotification[];
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
    paramNotificationUserId: any;
    owner: any;
    isAdmin: boolean;

    constructor(
        private notificationService: NotificationService,
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
            this.paramNotificationUserId = params.userIdEquals;
        });
    }

    loadAll() {
        const query = {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            };
        if ( this.paramNotificationUserId  != null) {
            query['userId.equals'] = this.paramNotificationUserId;
        }
        this.notificationService
            .query(query)
            .subscribe(
                (res: HttpResponse<INotification[]>) => this.paginateNotifications(res.body, res.headers),
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
        this.router.navigate(['/notification'], {
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
            '/notification',
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
//          console.log('CONSOLOG: M:ngOnInit & O: this.currentAccount : ',  this.currentAccount);
//          console.log('CONSOLOG: M:ngOnInit & O: this.owner : ',  this.owner);
            this.principal.hasAnyAuthority(['ROLE_ADMIN']).then( result => {
                this.isAdmin = result;
                if ( this.isAdmin === true ) {
                    this.loadAll();
                } else {
                    this.myNotifications();
                }
            });
        });
        this.registerChangeInNotifications();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INotification) {
        return item.id;
    }

    registerChangeInNotifications() {
        this.eventSubscriber = this.eventManager.subscribe('notificationListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    myNotifications() {
        const query = {
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.notificationService
            .query(query)
            .subscribe(
                    (res: HttpResponse<INotification[]>) => this.paginateNotifications(res.body, res.headers),
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private paginateNotifications(data: INotification[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.notifications = data;
        console.log('CONSOLOG: M:paginateNotifications & O: this.follows : ', this.owner);
        console.log('CONSOLOG: M:paginateNotifications & O: this.follows : ', this.isAdmin);
        console.log('CONSOLOG: M:paginateNotifications & O: this.follows : ', this.notifications);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
