package com.erlei.videorecorder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erlei.gdx.files.AndroidFiles;
import com.erlei.gdx.graphics.FrameBuffer;
import com.erlei.gdx.graphics.Texture;
import com.erlei.gdx.graphics.g2d.SpriteBatch;
import com.erlei.gdx.utils.Pixmap;
import com.erlei.videorecorder.R;
import com.erlei.videorecorder.RenderView;
import com.erlei.videorecorder.camera.Size;
import com.erlei.videorecorder.gles.EglCore;
import com.erlei.videorecorder.gles.EglSurfaceBase;
import com.erlei.videorecorder.util.LogUtil;

/**
 * Created by lll on 2018/9/14
 * Email : lllemail@foxmail.com
 * Describe : 测试GDX
 */
public class GdxTestFragment extends Fragment implements RenderView.Renderer {

    private static final String TAG = "GdxTestFragment";

    private FrameBuffer mFrameBuffer;

    public static GdxTestFragment newInstance() {
        return new GdxTestFragment();
    }

    private com.erlei.videorecorder.GLSurfaceView mSurfaceView;
    private SpriteBatch mBatch;
    private Texture mTexture;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gdx_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view) {
        mSurfaceView = view.findViewById(R.id.SurfaceView);
        mSurfaceView.setRenderer(this);
        mSurfaceView.setRenderMode(RenderView.RenderMode.WHEN_DIRTY);
    }

    @Override
    public void onSurfaceCreated(EglCore egl, EglSurfaceBase eglSurface) {
        LogUtil.logd(egl.toString()  + "------" +  eglSurface.toString()  + "------" +  mSurfaceView.getWidth() + "------" + mSurfaceView.getHeight());
        mFrameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, mSurfaceView.getWidth(), mSurfaceView.getHeight(), false);
        mBatch = new SpriteBatch(new Size(mSurfaceView.getWidth(), mSurfaceView.getHeight()));
        mTexture = new Texture(AndroidFiles.getInstance().internal("593522e9ea624.png"));
    }

    @Override
    public void onSurfaceChanged(int width, int height) {

    }


    @Override
    public void onDrawFrame() {
        long millis = System.currentTimeMillis();

        mFrameBuffer.begin();
        mBatch.begin();
        mBatch.draw(mTexture, 0, 0, mSurfaceView.getWidth(), mSurfaceView.getHeight());
        mBatch.end();
        mFrameBuffer.end();
        LogUtil.logd(TAG, String.valueOf(System.currentTimeMillis() - millis));


        millis = System.currentTimeMillis();
        mBatch.begin();
        mBatch.draw(mFrameBuffer.getColorBufferTexture(), 0, 0, mSurfaceView.getWidth(), mSurfaceView.getHeight(), 0, 0,
                mFrameBuffer.getColorBufferTexture().getWidth(),
                mFrameBuffer.getColorBufferTexture().getHeight(), false, true);
        mBatch.end();
        LogUtil.logd(TAG, String.valueOf(System.currentTimeMillis() - millis));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFrameBuffer.dispose();
        mTexture.dispose();
        mBatch.dispose();
    }


}
