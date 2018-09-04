import { Moment } from 'moment';
import { IBlog } from 'app/shared/model//blog.model';
import { IMessage } from 'app/shared/model//message.model';
import { IFollow } from 'app/shared/model//follow.model';
import { IBlockuser } from 'app/shared/model//blockuser.model';
import { ICalbum } from 'app/shared/model//calbum.model';
import { IInterest } from 'app/shared/model//interest.model';
import { IActivity } from 'app/shared/model//activity.model';
import { ICeleb } from 'app/shared/model//celeb.model';

export interface ICommunity {
    id?: number;
    creationDate?: Moment;
    communityname?: string;
    communitydescription?: string;
    imageContentType?: string;
    image?: any;
    isActive?: boolean;
    blogs?: IBlog[];
    messages?: IMessage[];
    cfolloweds?: IFollow[];
    cfollowings?: IFollow[];
    cblockedusers?: IBlockuser[];
    cblockingusers?: IBlockuser[];
    userId?: number;
    calbums?: ICalbum[];
    interests?: IInterest[];
    activities?: IActivity[];
    celebs?: ICeleb[];
}

export class Community implements ICommunity {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public communityname?: string,
        public communitydescription?: string,
        public imageContentType?: string,
        public image?: any,
        public isActive?: boolean,
        public blogs?: IBlog[],
        public messages?: IMessage[],
        public cfolloweds?: IFollow[],
        public cfollowings?: IFollow[],
        public cblockedusers?: IBlockuser[],
        public cblockingusers?: IBlockuser[],
        public userId?: number,
        public calbums?: ICalbum[],
        public interests?: IInterest[],
        public activities?: IActivity[],
        public celebs?: ICeleb[]
    ) {
        this.isActive = this.isActive || false;
    }
}
