import OpenAI from 'openai';
import fs from 'fs';
import { property, success } from 'zod';
const openai = new OpenAI();

export async function generateImage(prompt) {
  //Skip if image already exists
  let path = `images/${prompt}.png`;
  if (fs.existsSync(path)) {
    console.log(
      `[generateImage] Image for prompt ${prompt} already exists at: ${path}`
    );
    return { success: true, path: path };
  }

  console.log(`[generateImage] Generating image for prompt: "${prompt}"`);
  const result = await openai.images.generate({
    model: 'gpt-image-1',
    prompt,
  });
  const image_base64 = result.data[0].b64_json;
  const image_bytes = Buffer.from(image_base64, 'base64');
  fs.writeFileSync(path, image_bytes);
  console.log(`[generateImage] Image for prompt ${prompt} saved to: ${path}`);
  return { success: true, path: path };
}
