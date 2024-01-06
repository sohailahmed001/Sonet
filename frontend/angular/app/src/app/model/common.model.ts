import { SafeUrl } from "@angular/platform-browser";

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
    dob: string;
    gender: any;
    appUser: any;
}

export class Song {
    id: number;
    tempId: number;
    name: string;
    view: number;
    likes: number;
    createdDate: string;
    album: any;
    primaryPhotoUrl: string;
    primaryImageFile: any;
    selectedImageURL: string;
    audioFileUrl: string;
    uploadedFileName: string;
    audioFile: any;
}

export class Album {
    id: number;
    title: string;
    description: string;
    releaseDate: string;
    createdDate: string;
    artist: any;
    songs: any[];
    coverImageURL: string;
    coverImageFile: any;
    selectedImageURL: string;
}