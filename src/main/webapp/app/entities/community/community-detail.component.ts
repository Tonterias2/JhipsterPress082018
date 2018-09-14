import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';

import { ICommunity } from 'app/shared/model/community.model';
import { IBlog } from 'app/shared/model/blog.model';
import { BlogService } from 'app/entities/blog';

@Component({
    selector: 'jhi-community-detail',
    templateUrl: './community-detail.component.html'
})
export class CommunityDetailComponent implements OnInit {
    community: ICommunity;
    blogs: IBlog[];

    constructor(
        private blogService: BlogService,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private activatedRoute: ActivatedRoute
        ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ community }) => {
            this.community = community;
            this.communitiesBlogs( community );
            console.log('ngOnInit print this.community: ', this.community);
        });
    }

    private communitiesBlogs(community) {
        const query = {
            };
        if ( this.community != null) {
            query['communityId.in'] = community.id;
        }
        this.blogService
            .query(query)
            .subscribe(
            (res: HttpResponse<IBlog[]>) => {
                this.blogs = res.body;
                console.log('communitiesBlogs print this.blogs: ', this.blogs);
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
