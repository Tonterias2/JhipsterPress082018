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
    follow: IFollow;
    currentAccount: any;
    isFollow: boolean;
    loggedProfileId: number;
//    creationDate: string;
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
            console.log('PROFILE: ', this.profile);
        });
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.myProfiles();
        });
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
            query['profileId.in'] = this.loggedProfileId;
        }
        this.followService
            .query(query)
            .subscribe(
                    (res: HttpResponse<IFollow[]>) => {
                        this.follows = res.body;
                        this.isFollow = false;
                        this.follows.forEach(follow => {
                            if (follow.followingId === this.loggedProfileId) {
                                this.isFollow = true;
                                console.log('BINGO!!!! Folllows PROFILE: ', this.isFollow);
                                console.log('BINGO!!!! follow.followingId: ', follow.followingId);
                                console.log('BINGO!!!! this.profile.id: ', this.loggedProfileId);
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
//        this.follow = new Object();
        this.follow.creationDate = moment();
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
// tengo que localizar el id del registro a borrar y borrarlo. QUERY doble con   this.follow.followingId = this.profile.id;   this.follow.followedId =  this.loggedProfileId;
    }

    // BOTONES DE BLOCK Y UNBLOCK USER que más tarde pasaremos a los mensajes, pero ahora se quedan en el PROFILE

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

    previousState() {
        window.history.back();
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
