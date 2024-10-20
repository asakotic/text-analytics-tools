import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TokenComponent } from "./components/token/token.component";
import { EntityExtractionComponent } from "./components/entity-extraction/entity-extraction.component"
import { LanguageDetectionComponent } from './components/language-detection/language-detection.component';
import { SentimentAnalysisComponent } from './components/sentiment-analysis/sentiment-analysis.component';
import { TextSimilarityComponent } from './components/text-similarity/text-similarity.component';
import { HistoryComponent } from './components/history/history.component';

const routes: Routes = [
  {
    path:"",
    component : TokenComponent,
  },
  {
    path: "extraction",
    component: EntityExtractionComponent,
    canActivate: [],
    canDeactivate: [],
  },
  {
    path: "detection",
    component: LanguageDetectionComponent,
    canActivate: [],
    canDeactivate: [],
  },
  {
    path: "analysis",
    component: SentimentAnalysisComponent,
    canActivate: [],
    canDeactivate: [],
  },
  {
    path: "similarity",
    component: TextSimilarityComponent,
    canActivate: [],
    canDeactivate: [],
  },
  {
    path: "history",
    component: HistoryComponent,
    canActivate: [],
    canDeactivate: [],
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
