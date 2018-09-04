import { Moment } from 'moment';

export interface IFollow {
    id?: number;
    creationDate?: Moment;
    cfollowedId?: number;
    cfollowingId?: number;
    followedId?: number;
    followingId?: number;
}

export class Follow implements IFollow {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public cfollowedId?: number,
        public cfollowingId?: number,
        public followedId?: number,
        public followingId?: number
    ) {}
}
