import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from '../../entities/comment/comment.service';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile';

import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { Principal } from 'app/core';
import { JhiAlertService } from 'ng-jhipster';

// import { map } from 'rxjs/operators';

@Component({
    selector: 'jhi-post-detail',
    templateUrl: './post-detail.component.html'
})
export class PostDetailComponent implements OnInit {
    post: IPost;
    posts: IPost[];

    profiles: IProfile[];
    profile: IProfile;

    currentAccount: any;
    creationDate: string;
    id: any;

    commentText: string;
    private _comment: IComment;
    isSaving: boolean;

    constructor(
            private dataUtils: JhiDataUtils,
            private jhiAlertService: JhiAlertService,
            private commentService: CommentService,
            private postService: PostService,
            private principal: Principal,
            private profileService: ProfileService,
            private activatedRoute: ActivatedRoute
    ) {
        this._comment = {'commentText' : ''};
       // this.creationDate = moment().format(DATE_TIME_FORMAT);
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ post }) => {
            this.post = post;
            console.log('POST#', this.post);
        });
        this.principal.identity().then(account => {
            this.currentAccount = account;
            console.log('PRINCIPAL: ', this.currentAccount);
        });
//        this.comment = new Object();
    }

    save() {
        this.isSaving = true;
        this.creationDate = moment().format(DATE_TIME_FORMAT);
        this.comment.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.comment.id !== undefined) {
            this.subscribeToSaveResponse(this.commentService.update(this.comment));
        } else {
//            console.log('From SAVE()alCREATE print COMMENT: ', this.comment);
            this.comment.id = undefined;
            this.comment.postId = this.post.id;
            this.myProfile()
            .subscribe(
                    (res: HttpResponse<IProfile[]>) => {
                        this.profiles = res.body;
                        console.log('My comment.profileId: ', this.profiles[0].id);
                        this.id = this.profiles[0].id;
                        this.comment.profileId = this.id;
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.comment.isOffensive = false;
            console.log('READY 2 SAVE() print COMMENT2: ', this.comment);
            this.subscribeToSaveResponse(this.commentService.create(this.comment));
        }
    }

    private myProfile() {
        const query = {
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        return this.profileService
            .query(query);
    }
//    private myProfile() {
//        const query = {
//            };
//        if ( this.currentAccount.id  != null) {
//            query['userId.equals'] = this.currentAccount.id;
//        }
//        this.profileService
//            .query(query)
//            .subscribe(
//                    (res: HttpResponse<IProfile[]>) => {
//                        this.profiles = res.body;
//                        console.log('My Profiles: ', this.profiles);
////                        this.profilesMessages();
//                        // this.id = this.profiles.id;
//                        console.log('My Profiles: ', this.profiles[0].id);
//                        this.id = this.profiles[0].id;
//                        console.log('My ID: ', this.id);
//                        console.log(typeof this.id);
//                        return this.id;
////                        return this.profiles[0].id;
//                    },
//                    (res: HttpErrorResponse) => this.onError(res.message)
//            );
//    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    previousState() {
        window.history.back();
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
        console.log(this._comment);
        return this._comment;
    }

    set comment(comment: IComment) {
        // this._comment = comment;
//        this._comment.commentText = '';
        this.creationDate = moment().format(DATE_TIME_FORMAT);
    }
}
