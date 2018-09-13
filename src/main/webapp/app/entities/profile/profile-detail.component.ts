import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ProfileService } from 'app/entities/profile';
import { IProfile } from 'app/shared/model/profile.model';
import { FollowService } from '../follow/follow.service';
import { IFollow } from 'app/shared/model/follow.model';
import { IBlockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from '../blockuser/blockuser.service';

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
            private jhiAlertService: JhiAlertService,
            private activatedRoute: ActivatedRoute
            ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ profile }) => {
            this.profile = profile;
            console.log('VIEWED-PROFILE: ', this.profile);
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
            this.subscribeToSaveResponse( this.followService.create( this.follow ) );
            this.isFollowing = true;
            this.reload();
        }
    }

    private unFollowing() {
        if ( this.isFollowing === true ) {
            this.followService
                .delete( this.follows[0].id )
                .subscribe( response => { } );
            this.reload();
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
}
