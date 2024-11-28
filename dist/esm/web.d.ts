import { WebPlugin } from '@capacitor/core';
import type { NFCPluginPlugin } from './definitions';
export declare class NFCPluginWeb extends WebPlugin implements NFCPluginPlugin {
    readTag(): Promise<{
        message: string;
    }>;
    writeTag(options: {
        url: string;
    }): Promise<void>;
}
