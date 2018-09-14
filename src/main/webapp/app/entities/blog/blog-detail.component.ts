import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';

import { IBlog } from 'app/shared/model/blog.model';
import { IPost } from 'app/shared/model/post.model';
import { PostService } from 'app/entities/post';

@Component({
    selector: 'jhi-blog-detail',
    templateUrl: './blog-detail.component.html'
})
export class BlogDetailComponent implements OnInit {
    blog: IBlog;
    posts: IPost[];

constructor(
        private postService: PostService,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private activatedRoute: ActivatedRoute
        ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ blog }) => {
            this.blog = blog;
            this.communitiesPosts( blog );
            console.log('ngOnInit print this.blog: ', this.blog);
        });
    }

    private communitiesPosts(blog) {
        const query = {
            };
        if ( this.blog != null) {
            query['blogId.in'] = blog.id;
        }
        this.postService
            .query(query)
            .subscribe(
            (res: HttpResponse<IPost[]>) => {
                this.posts = res.body;
                console.log('communitiesBlogs print this.blogs: ', this.posts);
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
