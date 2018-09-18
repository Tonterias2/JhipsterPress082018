import { Moment } from 'moment';

export interface IBlockuser {
    id?: number;
    creationDate?: Moment;
    cblockeduserId?: number;
    cblockinguserId?: number;
    blockeduserId?: number;
    blockinguserId?: number;
}

export class Blockuser implements IBlockuser {
    constructor(
        public id?: number,
        public creationDate?: Moment,
        public cblockeduserId?: number,
        public cblockinguserId?: number,
        public blockeduserId?: number,
        public blockinguserId?: number
    ) {}
}
