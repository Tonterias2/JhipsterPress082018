import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from './album.service';
import { IUser, UserService } from 'app/core';

import { Principal } from 'app/core';

@Component({
    selector: 'jhi-album-update',
    templateUrl: './album-update.component.html'
})
export class AlbumUpdateComponent implements OnInit {
    private _album: IAlbum;
    isSaving: boolean;

    user: IUser;
    creationDate: string;
    currentAccount: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private albumService: AlbumService,
        private userService: UserService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ album }) => {
            this.album = album;
        });
        console.log('CONSOLOG: M:ngOnInit & O: this.isSaving : ', this.isSaving);
        this.principal.identity().then(account => {
            this.currentAccount = account;
            this.userServiceId(this.currentAccount);
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.album.userId = this.user.id;
        this.album.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.album.id !== undefined) {
            this.subscribeToSaveResponse(this.albumService.update(this.album));
        } else {
            this.subscribeToSaveResponse(this.albumService.create(this.album));
        }
    }

    private userServiceId(currentAccount) {
        this.userService.find(this.currentAccount.login).subscribe(
            (res: HttpResponse<IUser>) => {
                this.user = res.body;
                console.log('CONSOLOG: M:userServiceId & O: this.user : ', this.user);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        console.log('CONSOLOG: M:userServiceId & O: this.currentAccount.id : ', this.currentAccount.id);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAlbum>>) {
        result.subscribe((res: HttpResponse<IAlbum>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get album() {
        return this._album;
    }

    set album(album: IAlbum) {
        this._album = album;
        this.creationDate = moment(album.creationDate).format(DATE_TIME_FORMAT);
    }
}
