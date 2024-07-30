export interface NFCPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
