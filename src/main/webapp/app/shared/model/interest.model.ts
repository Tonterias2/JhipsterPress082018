import { ICommunity } from 'app/shared/model//community.model';
import { IProfile } from 'app/shared/model//profile.model';

export interface IInterest {
    id?: number;
    interestName?: string;
    communities?: ICommunity[];
    profiles?: IProfile[];
}

export class Interest implements IInterest {
    constructor(public id?: number, public interestName?: string, public communities?: ICommunity[], public profiles?: IProfile[]) {}
}
