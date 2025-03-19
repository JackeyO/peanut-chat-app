declare module 'qrcode.vue' {
  import { DefineComponent } from 'vue';

  const QrcodeVue: DefineComponent<{
    value: string;
    size?: number;
    level?: string;
    background?: string;
    foreground?: string;
    renderAs?: string;
  }>;

  export default QrcodeVue;
} 