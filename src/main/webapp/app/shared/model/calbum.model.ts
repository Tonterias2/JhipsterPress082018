import { Moment } from 'moment';
import { IPhoto } from 'app/shared/model//photo.model';

export interface ICalbum {
    id?: number;
    creationDate?: Moment;
    title?: string;
    photos?: IPhoto[];
    communityId?: number;
}

export class Calbum implements ICalbum {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public title?: string,
        public photos?: IPhoto[],
        public communityId?: number
    ) {}
}
