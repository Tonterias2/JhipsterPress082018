import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { ICalbum } from 'app/shared/model/calbum.model';
import { CalbumService } from './calbum.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community';

import { Principal } from 'app/core';

@Component({
    selector: 'jhi-calbum-update',
    templateUrl: './calbum-update.component.html'
})
export class CalbumUpdateComponent implements OnInit {
    private _calbum: ICalbum;
    isSaving: boolean;

    communities: ICommunity[];
    creationDate: string;
    currentAccount: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private calbumService: CalbumService,
        private communityService: CommunityService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ calbum }) => {
            this.calbum = calbum;
        });
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.myCommunities(this.currentAccount);
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.calbum.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.calbum.id !== undefined) {
            this.subscribeToSaveResponse(this.calbumService.update(this.calbum));
        } else {
            this.subscribeToSaveResponse(this.calbumService.create(this.calbum));
        }
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
                        console.log('CONSOLOG: M:myCommunities & O: res.body : ', res.body);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
        console.log('CONSOLOG: M:myCommunities & O: this.currentAccount.id : ', this.currentAccount.id);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICalbum>>) {
        result.subscribe((res: HttpResponse<ICalbum>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get calbum() {
        return this._calbum;
    }

    set calbum(calbum: ICalbum) {
        this._calbum = calbum;
        this.creationDate = moment(calbum.creationDate).format(DATE_TIME_FORMAT);
    }
}
