import { PersonalLibraryTO } from './personalLibraryTO';

export type userTO = {
    id: number;
    email: string;
    username: string;
    personalLibrary: PersonalLibraryTO;
};
