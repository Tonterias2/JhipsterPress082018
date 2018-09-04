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

@Component({
    selector: 'jhi-calbum-update',
    templateUrl: './calbum-update.component.html'
})
export class CalbumUpdateComponent implements OnInit {
    private _calbum: ICalbum;
    isSaving: boolean;

    communities: ICommunity[];
    creationDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private calbumService: CalbumService,
        private communityService: CommunityService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ calbum }) => {
            this.calbum = calbum;
        });
        this.communityService.query().subscribe(
            (res: HttpResponse<ICommunity[]>) => {
                this.communities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
