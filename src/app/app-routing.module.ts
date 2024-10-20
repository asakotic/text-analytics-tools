import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TokenComponent } from "./components/token/token.component";
import { EntityExtractionComponent } from "./components/entity-extraction/entity-extraction.component"
import { LanguageDetectionComponent } from './components/language-detection/language-detection.component';
import { SentimentAnalysisComponent } from './components/sentiment-analysis/sentiment-analysis.component';
import { TextSimilarityComponent } from './components/text-similarity/text-similarity.component';
import { HistoryComponent } from './components/history/history.component';
import { AuthGuard } from './auth/auth.guard';

const routes: Routes = [
  {
    path:"",
    component : TokenComponent,
  },
  {
    path: "extraction",
    component: EntityExtractionComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "detection",
    component: LanguageDetectionComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "analysis",
    component: SentimentAnalysisComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "similarity",
    component: TextSimilarityComponent,
    canActivate: [AuthGuard],
  },
  {
    path: "history",
    component: HistoryComponent,
    canActivate: [AuthGuard],
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
