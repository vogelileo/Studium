import OpenAI from 'openai';

const openai = new OpenAI();

/**
 * Generates speech audio from text using OpenAI's TTS API
 * @param {string} prompt - The text to convert to speech
 * @returns {Buffer} Audio data as a Buffer
 * @throws {Object} Error response with details if TTS generation fails
 */
export const generateVoice = async (prompt) => {
  const voice_model = 'tts-1';
  const voice_name = 'ash';

  console.log(`[TTS] Generating speech for: "${prompt.substring(0, 50)}..."`);

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

    // For robust server-side utility functions, it's better to throw an error
    // object than to try and send an HTTP response (which should be handled by the endpoint function itself).
    const errorResponseJson = {
      status: 500,
      code: 'TTS_API_ERROR',
      message: 'Failed to generate audio via OpenAI API.',
      details: error.message,
    };

    // Throw the structured error for the calling endpoint to catch and handle
    throw errorResponseJson;
  }
};
