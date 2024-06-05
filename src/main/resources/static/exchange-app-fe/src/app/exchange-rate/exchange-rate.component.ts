import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface HistoricalRate {
  targetCurrency: string;
  rate: number;
  date: string;
}

@Component({
  selector: 'app-exchange-rate',
  templateUrl: './exchange-rate.component.html',
  styleUrls: ['./exchange-rate.component.css'],
})
export class ExchangeRateComponent implements OnInit {
  eurAmount: number = 0;
  selectedCurrency: string = '';
  exchangeRate: number = 0;
  historicalRates: HistoricalRate[] = [];
  currencies: any[] = [];
  convertedAmount: number = 0;
  exchangeRatesMap: Map<string, number> = new Map<string, number>();
  exchangeRateAvailable = true;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<any>('http://localhost:8080/currencies').subscribe((data) => {
      this.currencies = data.currencies.map(
        (currency: any) => currency.currency
      );
      this.selectedCurrency = 'USD';
      this.eurAmount = 1;
      this.calculateExchange();
      this.fetchHistoricalRates();
    });
  }

  onAmountInput(event: any) {
    this.calculateExchange();
  }

  calculateExchange() {
    if (this.selectedCurrency && this.eurAmount) {
      if (this.exchangeRatesMap.has(this.selectedCurrency)) {
        // Use cached exchange rate
        this.exchangeRate = this.exchangeRatesMap.get(this.selectedCurrency)!;
        this.updateConvertedAmount();
      } else {
        this.http
          .get<{
            rate: {
              currency: string | null;
              rate: number | null;
              date: string | null;
            };
          }>('http://localhost:8080/exchangeRate', {
            params: { targetCurrency: this.selectedCurrency },
          })
          .subscribe((response) => {
            const rateResponse = response.rate;
            if (
              rateResponse &&
              rateResponse.currency !== null &&
              rateResponse.rate !== null &&
              rateResponse.date !== null
            ) {
              this.exchangeRate = rateResponse.rate;
              this.exchangeRatesMap.set(
                this.selectedCurrency,
                this.exchangeRate
              );
              this.updateConvertedAmount();
              this.exchangeRateAvailable = true; // Set flag to indicate exchange rate is available
            } else {
              // Set exchangeRate to indicate no exchange rate found
              this.exchangeRate = -1; // Special value to indicate no exchange rate found
              this.exchangeRateAvailable = false; // Set flag to indicate no exchange rate is available
            }
          });
      }
    }
  }

  updateConvertedAmount() {
    this.convertedAmount = this.eurAmount * this.exchangeRate;
  }

  fetchHistoricalRates() {
    if (this.selectedCurrency) {
      this.http
        .get<any>('http://localhost:8080/history', {
          params: { currency: this.selectedCurrency },
        })
        .subscribe((data) => {
          console.log(data);
          this.historicalRates = data.rates;
        });
    }
  }

  onCurrencyChange() {
    // Set the eurAmount back to 1 whenever the currency selection changes
    this.eurAmount = 1;
    this.calculateExchange();
    this.fetchHistoricalRates();
  }
}
