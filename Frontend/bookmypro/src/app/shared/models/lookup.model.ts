export interface LookupOption {
  id: string;
  code: string;
  name: string;
  parentValue?: string;
}

export interface LookupResponse {
  [key: string]: LookupOption[];
}
