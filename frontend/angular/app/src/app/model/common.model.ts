import { AppUser } from "./app-user.model";

export class Role {
    id: number;
    name: string;
    authorities: any[];
}

export class SonetUser {
    id: number;
    firstName: string;
    middleName: string;
    lastName: string;
    photo: any;
    dob: Date;
    gender: any;
    appUser: any;

    constructor() {
        this.appUser = new AppUser();
    }
}