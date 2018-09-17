import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ProfileService } from 'app/entities/profile';
import { IProfile } from 'app/shared/model/profile.model';
import { FollowService } from '../follow/follow.service';
import { IFollow } from 'app/shared/model/follow.model';
import { IBlockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from '../blockuser/blockuser.service';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from '../notification/notification.service';
// import { IUser, UserService } from 'app/core';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { Principal } from 'app/core';

@Component({
    selector: 'jhi-profile-detail',
    templateUrl: './profile-detail.component.html'
})
export class ProfileDetailComponent implements OnInit {
    profile: IProfile;
    profiles: IProfile[];
    loggedProfile: IProfile[];

    follows: IFollow[];
    private _follow: IFollow;

    blockusers: IBlockuser[];
    private _blockuser: IBlockuser;

    userId: number;
    private _notification: INotification;
    notificationDate: string;
    notificationReason: any;

    currentAccount: any;
    isFollowing: boolean;
    isBlocked: boolean;
    loggedProfileId: number;
    creationDate: string;
    isSaving: boolean;

    constructor(
            private dataUtils: JhiDataUtils,
            private principal: Principal,
            private profileService: ProfileService,
            private followService: FollowService,
            private blockuserService: BlockuserService,
            private notificationService: NotificationService,
            private jhiAlertService: JhiAlertService,
            private activatedRoute: ActivatedRoute
            ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ profile }) => {
            this.profile = profile;
            this.userId = profile.userId;
            console.log('CONSOLOG: M:ngOnInit & O: this.profile : ', this.profile);
        });
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.currentLoggedProfile();
        });
        this.isSaving = false;
        this.follow = new Object();
        this.blockuser = new Object();
    }

    private currentLoggedProfile() {
        const query = {
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.profileService
            .query(query)
            .subscribe(
                    (res: HttpResponse<IProfile[]>) => {
                        this.loggedProfile = res.body;
                        this.loggedProfile.forEach(profile => {
                            this.loggedProfileId = profile.id;
                        });
                        this.isFollower();
                        this.isBlockUser();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private isFollower( ) {
        this.isFollowing = false;
        const query = {
        };
    if ( this.currentAccount.id != null ) {
        query['followedId.in'] = this.loggedProfileId;
        query['followingId.in'] = this.profile.id;
    }
    this.followService
        .query(query)
        .subscribe(
            ( res: HttpResponse<IFollow[]> ) => {
                this.follows = res.body;
                if ( this.follows.length > 0) {
                    this.isFollowing = true;
                    return this.follows[0];
                }
            },
            ( res: HttpErrorResponse ) => this.onError( res.message )
        );
    }

    private following() {
        this.isSaving = true;
        this.follow.creationDate = moment( this.creationDate, DATE_TIME_FORMAT );
        this.follow.followingId = this.profile.id;
        this.follow.followedId = this.loggedProfileId;
        if ( this.isFollowing === false ) {
            console.log('CONSOLOG: M:following & O: this.follow : ', this.follow);
            this.subscribeToSaveResponse( this.followService.create( this.follow ) );
            this.notificationReason = 'FOLLOWING';
            this.createNotification( this.notificationReason );
            this.isFollowing = true;
            this.reload();
        }
    }

    private unFollowing() {
        if ( this.isFollowing === true ) {
            this.isFollower();
            console.log('CONSOLOG: M:unFollowing & O: this.follows[0].id : ', this.follows[0].id);
            this.followService
                .delete( this.follows[0].id )
                .subscribe( response => {
                    this.notificationReason = 'UNFOLLOWING';
                    this.createNotification( this.notificationReason );
                } );
            this.reload();
        }
    }

    private createNotification(notificationReason) {
        this.notification = new Object();
        console.log('CONSOLOG: M:createNotification & O: this.notification : ', this.notification);
        console.log('CONSOLOG: M:createNotification & O: this.userId : ', this.userId);
        this.isSaving = true;
        this.notification.creationDate = moment( this.creationDate, DATE_TIME_FORMAT );
        this.notification.notificationDate = moment( this.creationDate, DATE_TIME_FORMAT );
        this.notification.notificationReason = notificationReason;
//        this.notification.notificationText = notificationReason + ': ' + this.profile.lastName + ' ' + profile.lastName;
        this.notification.notificationText = notificationReason;
        this.notification.isDeliverd = false;
        this.notification.userId = this.userId;
        if (this.notification.id !== undefined) {
            this.subscribeToSaveResponse2(this.notificationService.update(this.notification));
        } else {
            console.log('CONSOLOG: M:createNotification & O: this.notification: ', this.notification);
            this.subscribeToSaveResponse2(this.notificationService.create(this.notification));
        }
    }
// BOTONES DE BLOCK Y UNBLOCK USER que más tarde pasaremos a los mensajes, pero ahora se quedan en el PROFILE

    private isBlockUser( ) {
        this.isBlocked = false;
        const query = {
        };
    if ( this.currentAccount.id != null ) {
        query['blockeduserId.in'] = this.loggedProfileId;
        query['blockinguserId.in'] = this.profile.id;
    }
    this.blockuserService
        .query(query)
        .subscribe(
            ( res: HttpResponse<IBlockuser[]> ) => {
                this.blockusers = res.body;
                if ( this.blockusers.length > 0) {
                    this.isBlocked = true;
                    return this.blockusers[0];
                }
            },
            ( res: HttpErrorResponse ) => this.onError( res.message )
        );
    }

    private blocking() {
        this.isSaving = true;
        this.blockuser.creationDate = moment( this.creationDate, DATE_TIME_FORMAT );
        this.blockuser.blockinguserId = this.profile.id;
        this.blockuser.blockeduserId = this.loggedProfileId;
        if ( this.isBlocked === false ) {
            this.subscribeToSaveResponse( this.blockuserService.create( this.blockuser ) );
            this.isBlocked = true;
            this.reload();
        }
    }

    private unBlocking() {
        if ( this.isBlocked === true ) {
            this.blockuserService
                .delete( this.blockusers[0].id )
                .subscribe( response => { } );
            this.reload();
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFollow>>) {
        result.subscribe((res: HttpResponse<IFollow>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private subscribeToSaveResponse2(result: Observable<HttpResponse<INotification>>) {
        result.subscribe((res: HttpResponse<INotification>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    reload() {
        window.location.reload();
    }
    previousState() {
        window.history.back();
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    get follow() {
        return this._follow;
    }

    set follow(follow: IFollow) {
        this._follow = follow;
        this.creationDate = moment().format(DATE_TIME_FORMAT);
    }

    get blockuser() {
        return this._blockuser;
    }

    set blockuser(blockuser: IBlockuser) {
        this._blockuser = blockuser;
        this.creationDate = moment().format(DATE_TIME_FORMAT);
    }

    get notification() {
        return this._notification;
    }

    set notification(notification: INotification) {
        this._notification = notification;
        this.creationDate = moment().format(DATE_TIME_FORMAT);
        this.notificationDate = moment().format(DATE_TIME_FORMAT);
        this.notificationReason = '';
    }
}
