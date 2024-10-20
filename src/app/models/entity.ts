export interface Entity {
    annotations: Annotation[];
}

export interface Annotation {
    abstract: string;
    categories: string[];
    image: Image;
}

export interface Image {
    full: string;
}