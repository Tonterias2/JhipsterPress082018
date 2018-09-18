import { ICommunity } from 'app/shared/model//community.model';
import { IProfile } from 'app/shared/model//profile.model';

export interface IActivity {
    id?: number;
    activityName?: string;
    communities?: ICommunity[];
    profiles?: IProfile[];
}

export class Activity implements IActivity {
    constructor(public id?: number, public activityName?: string, public communities?: ICommunity[], public profiles?: IProfile[]) {}
}
