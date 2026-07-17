import { Injectable, signal } from '@angular/core';
import { LookupResponse } from '@shared/models/lookup.model';

@Injectable()
export class LookUpStore {
  private readonly _lookups = signal<LookupResponse>({});

  readonly lookups = this._lookups.asReadonly();

  getLookupByType(key: string): { label: string; value: string }[] {
    return (this._lookups()[key] ?? []).map((item) => ({
      label: item.name,
      value: item.id,
    }));
  }

  setLookups(response: LookupResponse): void {
    this._lookups.set(response);
  }

  clear(): void {
    this._lookups.set({});
  }
}
