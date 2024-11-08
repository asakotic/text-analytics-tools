export interface Lang {
    detectedLangs: DetectedLang[];
}

interface DetectedLang {
    lang: string;
    confidence: number;
}