import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPost } from 'app/shared/model/post.model';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from '../../entities/comment/comment.service';
import { PostService } from 'app/entities/post';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile';

import { map } from 'rxjs/operators';

@Component({
    selector: 'jhi-post-detail',
    templateUrl: './post-detail.component.html'
})
export class PostDetailComponent implements OnInit {
    post: IPost;

    private _comment: IComment;
    isSaving: boolean;

    posts: IPost[];

    profiles: IProfile[];
    profile: IProfile;
    creationDate: string;

    constructor(
            private dataUtils: JhiDataUtils,
            private jhiAlertService: JhiAlertService,
            private commentService: CommentService,
            private postService: PostService,
            private profileService: ProfileService,
            private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
//        this.loadAll();
//        this.principal.identity().then(account => {
//            this.currentAccount = account;
//        });
        this.activatedRoute.data.subscribe(({ post }) => {
            this.post = post;
            console.log('POST#', this.post);
        });
        this.activatedRoute.data.subscribe(({ comment }) => {
            this.comment = comment;
            console.log('COMMENT#', this.comment);
        });
        this.postService.query().subscribe(
            (res: HttpResponse<IPost[]>) => {
                this.posts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.profileService.query().subscribe(
            (res: HttpResponse<IProfile[]>) => {
                this.profiles = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.isSaving = false;
        this.comment = new Object();
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

    save() {
        this.isSaving = true;
        console.log('From SAVE() print COMMENT: ', this.comment);
        this.comment.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.comment.id !== undefined) {
            console.log('From SAVE()alUPDATE print COMMENT: ');
            this.subscribeToSaveResponse(this.commentService.update(this.comment));
        } else {
            console.log('From SAVE()alCREATE print COMMENT: ', this.comment.profileId);
            // AQUI TENDRIA QUE CARGAR EL PROFILE DEL COLEGA
            this.comment.isOffensive = false;
//            this.myProfile(this.comment.profileId);
//            this.service.find(id).map((profile: HttpResponse<Profile>) => profile.body);
            this.subscribeToSaveResponse(this.commentService.create(this.comment));
        }
    }

    private myProfile(profileId) {
//        return this.service.find(id).map((profile: HttpResponse<Profile>) => profile.body);
        this.profileService
            .find(profileId)
            .pipe(map((profile: HttpResponse<IProfile>) => {
                console.log('profile.body???:', profileId);
                console.log('profile.body???:', this.profile);
                return profile.body;
                }));
//            .subscribe(
//                    (res: HttpResponse<IProfile[]>) => {
//                        if ( res.body.length !== 0 ) {
////                            this.hasProfile = true;
////                            console.log('hasProfile1???:', this.hasProfile);
//                            console.log('res.body???:', res.body);
//                        }
////                        this.paginateProfiles(res.body, res.headers);
//                    } , (res: HttpErrorResponse) => this.onError(res.message)
//            );
////        console.log('hasProfile2???:', this.hasProfile);
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
        result.subscribe((res: HttpResponse<IComment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPostById(index: number, item: IPost) {
        return item.id;
    }

    trackProfileById(index: number, item: IProfile) {
        return item.id;
    }

    get comment() {
        return this._comment;
    }

    set comment(comment: IComment) {
        this._comment = comment;
        this.creationDate = moment().format(DATE_TIME_FORMAT);
    }
}
