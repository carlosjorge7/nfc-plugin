export interface NFCPluginPlugin {
  readTag(): Promise<{ message: string }>;
  writeTag(options: { message: string }): Promise<void>;
}
