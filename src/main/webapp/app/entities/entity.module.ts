import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterPress08BlogModule } from './blog/blog.module';
import { JhipsterPress08PostModule } from './post/post.module';
import { JhipsterPress08TopicModule } from './topic/topic.module';
import { JhipsterPress08TagModule } from './tag/tag.module';
import { JhipsterPress08CommentModule } from './comment/comment.module';
import { JhipsterPress08MessageModule } from './message/message.module';
import { JhipsterPress08NotificationModule } from './notification/notification.module';
import { JhipsterPress08ProfileModule } from './profile/profile.module';
import { JhipsterPress08CommunityModule } from './community/community.module';
import { JhipsterPress08FollowModule } from './follow/follow.module';
import { JhipsterPress08BlockuserModule } from './blockuser/blockuser.module';
import { JhipsterPress08AlbumModule } from './album/album.module';
import { JhipsterPress08CalbumModule } from './calbum/calbum.module';
import { JhipsterPress08PhotoModule } from './photo/photo.module';
import { JhipsterPress08InterestModule } from './interest/interest.module';
import { JhipsterPress08ActivityModule } from './activity/activity.module';
import { JhipsterPress08CelebModule } from './celeb/celeb.module';
import { JhipsterPress08UrllinkModule } from './urllink/urllink.module';
import { JhipsterPress08FrontpageconfigModule } from './frontpageconfig/frontpageconfig.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterPress08BlogModule,
        JhipsterPress08PostModule,
        JhipsterPress08TopicModule,
        JhipsterPress08TagModule,
        JhipsterPress08CommentModule,
        JhipsterPress08MessageModule,
        JhipsterPress08NotificationModule,
        JhipsterPress08ProfileModule,
        JhipsterPress08CommunityModule,
        JhipsterPress08FollowModule,
        JhipsterPress08BlockuserModule,
        JhipsterPress08AlbumModule,
        JhipsterPress08CalbumModule,
        JhipsterPress08PhotoModule,
        JhipsterPress08InterestModule,
        JhipsterPress08ActivityModule,
        JhipsterPress08CelebModule,
        JhipsterPress08UrllinkModule,
        JhipsterPress08FrontpageconfigModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterPress08EntityModule {}
