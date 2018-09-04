import { Moment } from 'moment';

export interface IComment {
    id?: number;
    creationDate?: Moment;
    commentText?: string;
    isOffensive?: boolean;
    postId?: number;
    profileId?: number;
}

export class Comment implements IComment {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public commentText?: string,
        public isOffensive?: boolean,
        public postId?: number,
        public profileId?: number
    ) {
        this.isOffensive = this.isOffensive || false;
    }
}
