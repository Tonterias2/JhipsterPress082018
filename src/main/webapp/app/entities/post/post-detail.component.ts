import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { Principal } from 'app/core';

import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from 'app/entities/comment';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile';

@Component({
    selector: 'jhi-post-detail',
    templateUrl: './post-detail.component.html'
})
export class PostDetailComponent implements OnInit {
    private _comment: IComment;
    isSaving: boolean;

    post: IPost;
    posts: IPost[];

    profile: IProfile;
    profiles: IProfile[];

    currentAccount: any;
    creationDate: string;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private commentService: CommentService,
        private postService: PostService,
        private principal: Principal,
        private profileService: ProfileService,
        private activatedRoute: ActivatedRoute
    ) { }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ post }) => {
            this.post = post;
            console.log('ngOnInit - POST#', this.post);
        });
        this.principal.identity().then(account => {
            this.currentAccount = account;
            console.log('PRINCIPAL: ', this.currentAccount);
        });
        this.comment = new Object();
        this.comment.commentText = '';
    }

    save() {
        this.isSaving = true;
        console.log('save My comment.id: ', this.comment.id);
        this.comment.creationDate = moment(this.creationDate, DATE_TIME_FORMAT);
        if (this.comment.id !== undefined) {
            this.subscribeToSaveResponse(this.commentService.update(this.comment));
        } else {
            console.log('READY 1 SAVE() print COMMENT1: ', this.comment);
            this.comment.postId = this.post.id;
            this.loggedProfile()
            .subscribe(
                    (res: HttpResponse<IProfile[]>) => {
                        this.profiles = res.body;
                        console.log('save My comment.profileId: ', this.profiles[0].id);
                        console.log('save My comment.profileId: ', this.profiles);
//                        this.id = this.profiles[0].id;
                        this.comment.profileId = this.profiles[0].id;
                        console.log('save My this.comment.profileId: ', this.comment.profileId);
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
            );
            this.comment.isOffensive = false;
            console.log('READY 2 SAVE() print COMMENT2: ', this.comment);
            this.subscribeToSaveResponse(this.commentService.create(this.comment));
        }
    }

    private loggedProfile() {
        console.log('ENTRO loggedProfile this.currentAccount.id: ', this.currentAccount.id);
        const query = {
            };
        if ( this.currentAccount.id  != null) {
            query['userId.equals'] = this.currentAccount.id;
        }
        return this.profileService
            .query(query);
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
//        this._comment.commentText = '';
        this.creationDate = moment(comment.creationDate).format(DATE_TIME_FORMAT);
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
//        window.history.back();
    }
}
