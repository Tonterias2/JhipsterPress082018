import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IBlockuser } from 'app/shared/model/blockuser.model';
import { BlockuserService } from './blockuser.service';
import { ICommunity } from 'app/shared/model/community.model';
import { CommunityService } from 'app/entities/community';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile';

@Component({
    selector: 'jhi-blockuser-update',
    templateUrl: './blockuser-update.component.html'
})
export class BlockuserUpdateComponent implements OnInit {
    private _blockuser: IBlockuser;
    isSaving: boolean;

    communities: ICommunity[];

    profiles: IProfile[];
    creationDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private blockuserService: BlockuserService,
        private communityService: CommunityService,
        private profileService: ProfileService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ blockuser }) => {
            this.blockuser = blockuser;
        });
        this.communityService.query().subscribe(
            (res: HttpResponse<ICommunity[]>) => {
                this.communities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.profileService.query().subscribe(
            (res: HttpResponse<IProfile[]>) => {
                this.profiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.blockuser.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.blockuser.id !== undefined) {
            this.subscribeToSaveResponse(this.blockuserService.update(this.blockuser));
        } else {
            this.subscribeToSaveResponse(this.blockuserService.create(this.blockuser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBlockuser>>) {
        result.subscribe((res: HttpResponse<IBlockuser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get blockuser() {
        return this._blockuser;
    }

    set blockuser(blockuser: IBlockuser) {
        this._blockuser = blockuser;
        this.creationDate = moment(blockuser.creationDate).format(DATE_TIME_FORMAT);
    }
}
