import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ProfileService } from 'app/entities/profile';
import { IProfile } from 'app/shared/model/profile.model';
import { FollowService } from '../follow/follow.service';
import { IFollow } from 'app/shared/model/follow.model';
import { Principal } from 'app/core';
import { JhiAlertService } from 'ng-jhipster';

import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

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

    currentAccount: any;
    isFollow: boolean;
    loggedProfileId: number;
    creationDate: string;
    isSaving: boolean;

    constructor(
            private dataUtils: JhiDataUtils,
            private principal: Principal,
            private profileService: ProfileService,
            private followService: FollowService,
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
            this.myProfiles();
        });
        this.isSaving = false;
        this.follow = new Object();
    }

    private myProfiles() {
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
                        this.profilesFollows();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private profilesFollows() {
        const query = {
            };
        if ( this.loggedProfile  != null) {
            this.loggedProfile.forEach(profile => {
                this.loggedProfileId = profile.id;
            });
            query['followedId.in'] = this.loggedProfileId;
        }
        this.followService
            .query(query)
            .subscribe(
                    (res: HttpResponse<IFollow[]>) => {
                        this.follows = res.body;
                        this.isFollow = false;
                        console.log('ATENCION!!!! LISTA.follows: ', this.follows);
                        console.log('ATENCION!!!! this.profile.id: ', this.profile.id);
                        this.follows.forEach(follow => {
                            if (follow.followingId === this.profile.id) {
                                this.isFollow = true;
                                console.log('BINGO!!!! Folllows PROFILE isFollowed: ', this.isFollow);
                                console.log('BINGO!!!! follow.followingId: ', follow.followingId);
                                console.log('BINGO 1 ! loggedProfileId FOLLOW-ED: ', this.loggedProfileId);
                                console.log('BINGO!!!! Folllows PROFILE: ', follow);
                            }
                            console.log('NOOOO BINGO!!!! Folllows PROFILE: ', this.isFollow);
                        });
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    following() {
        console.log('BINGO!!!! Folllow: ', this.follow);
        this.isSaving = true;
        this.follow.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        this.follow.followingId = this.profile.id;
        this.follow.followedId =  this.loggedProfileId;
        console.log('BINGO!!!! Folllow: ', this.follow);
        if ( this.follow.id !== undefined ) {
            console.log( 'From SAVE()al UPDATE print FOLLOWS: ' );
            this.subscribeToSaveResponse( this.followService.update( this.follow ) );
        } else {
            console.log( 'From SAVE()al UPDATE print FOLLOWS: ' );
            this.subscribeToSaveResponse( this.followService.create( this.follow ) );
        }
    }

    unFollowing() {
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
                    console.log('BINGO!!!! Folllow RESPONDE: ', this.follow);
                    this.followService
                        .delete( this.follows[0].id )
                        .subscribe( response => { } );
                    this.previousState();
                },
                ( res: HttpErrorResponse ) => this.onError( res.message )
            );
    }
// BOTONES DE BLOCK Y UNBLOCK USER que más tarde pasaremos a los mensajes, pero ahora se quedan en el PROFILE

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFollow>>) {
        result.subscribe((res: HttpResponse<IFollow>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
//        this.previousState();
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
}
