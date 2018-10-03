import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IInterest } from 'app/shared/model/interest.model';
import { InterestService } from './interest.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile';
import { Principal } from 'app/core';

@Component({
    selector: 'jhi-interest-update',
    templateUrl: './interest-update.component.html'
})
export class InterestUpdateComponent implements OnInit {
    private _interest: IInterest;
    isSaving: boolean;

    communities: ICommunity[];

    profiles: IProfile[];

    currentAccount: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private interestService: InterestService,
        private communityService: CommunityService,
        private profileService: ProfileService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ interest }) => {
            this.interest = interest;
        });
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.myCommunities(this.currentAccount);
            this.myProfiles(this.currentAccount);
        });
//        this.communityService.query().subscribe(
//            (res: HttpResponse<ICommunity[]>) => {
//                this.communities = res.body;
//            },
//            (res: HttpErrorResponse) => this.onError(res.message)
//        );
//        this.profileService.query().subscribe(
//            (res: HttpResponse<IProfile[]>) => {
//                this.profiles = res.body;
//            },
//            (res: HttpErrorResponse) => this.onError(res.message)
//        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.interest.id !== undefined) {
            this.subscribeToSaveResponse(this.interestService.update(this.interest));
        } else {
            this.subscribeToSaveResponse(this.interestService.create(this.interest));
        }
    }

    private myProfiles(currentAccount) {
        const query = {
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.profileService
            .query(query)
            .subscribe(
                    (res: HttpResponse<IProfile[]>) => {
                        this.profiles = res.body;
                        console.log('CONSOLOG: M:myProfiles & O: res.body : ', res.body);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    private myCommunities(currentAccount) {
        const query = {
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        this.communityService
            .query(query)
            .subscribe(
                    (res: HttpResponse<ICommunity[]>) => {
                        this.communities = res.body;
                        console.log('CONSOLOG: M:paginateFollows & O: res.body : ', res.body);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
        console.log('CONSOLOG: M:paginateFollows & O: this.currentAccount.id : ', this.currentAccount.id);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInterest>>) {
        result.subscribe((res: HttpResponse<IInterest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCommunityById(index: number, item: ICommunity) {
        return item.id;
    }

    trackProfileById(index: number, item: IProfile) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get interest() {
        return this._interest;
    }

    set interest(interest: IInterest) {
        this._interest = interest;
    }
}
