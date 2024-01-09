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
    duration: number;
    tempAudioSrc: string;
    uploadedValue: number;
    artistName: string;
}

export class Album {
    id: number;
    title: string;
    description: string;
    published: boolean;
    releaseDate: string;
    createdDate: string;
    artist: any;
    artistName: string;
    songs: any[];
    coverImageURL: string;
    coverImageFile: any;
    selectedImageURL: string;
    liked: boolean;
    likes: number;
}