import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ExchangeRateComponent } from './exchange-rate/exchange-rate.component';

const routes: Routes = [{ path: '', component: ExchangeRateComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
