import { ICommunity } from 'app/shared/model//community.model';
import { IProfile } from 'app/shared/model//profile.model';

export interface ICeleb {
    id?: number;
    celebName?: string;
    communities?: ICommunity[];
    profiles?: IProfile[];
}

export class Celeb implements ICeleb {
    constructor(public id?: number, public celebName?: string, public communities?: ICommunity[], public profiles?: IProfile[]) {}
}
