import OpenAI from 'openai';

const openai = new OpenAI();

/**
 * Generates speech audio from text using OpenAI's TTS service.
 * @param {string} prompt - Text to convert into speech audio.
 * @returns {Buffer} Audio data buffer containing the generated speech.
 */
export const generateVoice = async (prompt) => {
  const voice_model = 'tts-1';
  const voice_name = 'ash';

  console.log(`[TTS] Generating speech for: \"${prompt.substring(0, 50)}...\"`);

  try {
    const outputDir = path.dirname(outputFilePath);
    await fs.mkdir(outputDir, { recursive: true });

    const response = await openai.audio.speech.create({
      model: voice_model,
      voice: voice_name,
      input: prompt,
      speed: 1.0,
    });

    return Buffer.from(await response.arrayBuffer());
  } catch (error) {
    console.error('[TTS] Error during generation or streaming:', error.message);

    const errorResponseJson = {
      status: 500,
      code: 'TTS_API_ERROR',
      message: 'Failed to generate audio via OpenAI API.',
      details: error.message,
    };

    throw errorResponseJson;
  }
};
