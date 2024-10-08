package kr.kymedia.karaoke.play.soundtouch;

import java.io.IOException;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import com.smp.soundtouchandroid.AudioSink;
import com.smp.soundtouchandroid.AudioTrackAudioSink;
import com.smp.soundtouchandroid.SoundStreamRuntimeException;

public class SoundStreamAudioPlayer extends SoundStreamRunnable
{
	private static final int BUFFER_SIZE_TRACK = 32768;

	private volatile AudioTrackAudioSink track;

	public SoundStreamAudioPlayer(Context context, int id, String fileName, float tempo, float pitchSemi) throws IOException
	{
		super(context, id, fileName, tempo, pitchSemi);
	}

	public int getSessionId()
	{
		return track.getAudioSessionId();
	}

	public long getAudioTrackBufferSize()
	{
		synchronized (sinkLock)
		{
			long playbackHead = track.getPlaybackHeadPosition() & 0xffffffffL;
			return bytesWritten - playbackHead * DEFAULT_BYTES_PER_SAMPLE
					* getChannels();
		}
	}

	public void setVolume(float left, float right)
	{
		synchronized (sinkLock)
		{
			track.setStereoVolume(left, right);
		}
	}

	public boolean isInitialized()
	{
		return track.getState() == AudioTrack.STATE_INITIALIZED;
	}

	public void seekTo(double percentage, boolean shouldFlush) // 0.0 - 1.0
	{
		long timeInUs = (long) (decoder.getDuration() * percentage);
		seekTo(timeInUs, shouldFlush);
	}

	public void seekTo(long timeInUs, boolean shouldFlush)
	{
		if (timeInUs < 0 || timeInUs > decoder.getDuration())
			throw new SoundStreamRuntimeException("" + timeInUs
					+ " Not a valid seek time.");

		if (shouldFlush)
		{
			this.pause();
			synchronized (sinkLock)
			{
				track.flush();
				bytesWritten = 0;
			}
			soundTouch.clearBuffer();
		}
		synchronized (decodeLock)
		{
			decoder.seek(timeInUs, shouldFlush);
		}
	}

	@Override
	public void onStart()
	{
		synchronized (sinkLock)
		{
			track.play();
		}
	}

	@Override
	public void onPause()
	{
		synchronized (sinkLock)
		{
			track.pause();
		}
	}

	@Override
	public void onStop()
	{
	}

	@Override
	public void seekTo(long timeInUs)
	{
		seekTo(timeInUs, false);
	}

	private void initAudioTrack(int id, float tempo, float pitchSemi)
			throws IOException
	{
		int channelFormat;

		if (channels == 1)
			channelFormat = AudioFormat.CHANNEL_OUT_MONO;
		else if (channels == 2)
			channelFormat = AudioFormat.CHANNEL_OUT_STEREO;
		else
			throw new SoundStreamRuntimeException(
					"Valid channel count is 1 or 2");

		track = new AudioTrackAudioSink(AudioManager.STREAM_MUSIC,
				samplingRate, channelFormat, AudioFormat.ENCODING_PCM_16BIT,
				BUFFER_SIZE_TRACK, AudioTrack.MODE_STREAM);
	}

	@Override
	protected AudioSink initAudioSink() throws IOException
	{
		initAudioTrack(getSoundTouchTrackId(), getTempo(), getPitchSemi());
		return track;
	}

}
