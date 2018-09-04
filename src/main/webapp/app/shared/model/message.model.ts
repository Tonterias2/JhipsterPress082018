import { Moment } from 'moment';

export interface IMessage {
    id?: number;
    creationDate?: Moment;
    messageText?: string;
    isDeliverd?: boolean;
    communityId?: number;
    profileId?: number;
}

export class Message implements IMessage {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public messageText?: string,
        public isDeliverd?: boolean,
        public communityId?: number,
        public profileId?: number
    ) {
        this.isDeliverd = this.isDeliverd || false;
    }
}
