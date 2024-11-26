import { PluginListenerHandle } from '@capacitor/core';

export interface NFCPluginPlugin {
  readTag(): Promise<{ message: string }>;
  writeTag(options: { url: string }): Promise<void>;
  // Añadir el método addListener para gestionar los eventos
  addListener(
    eventName: string,
    listenerFunc: (info: any) => void,
  ): PluginListenerHandle;
}
