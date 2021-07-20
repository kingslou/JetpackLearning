package com.geen.jetpacklearning.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.geen.jetpacklearning.R;

/**
 * Created by Pang on 2017/8/5.
 */

public class CircleProgress extends View {

    // 画进度条按钮的画笔
    private Paint mThumbPaint;
    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画圆环的画笔背景色
    private Paint mRingPaintBg;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 圆环背景颜色
    private int mRingBgColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private int mProgress;

    private Bitmap mThumbBitmap;
    private Point mThumbPoint;
    private boolean mFirst = true;
    // 按钮宽度
    private float mThumbWith;
    private float mThumbHeight ;
    double mLastAngleR;
    // 按钮图标
    private Drawable mThumbDrawable ;
    // 按钮是否随着角度旋转
    private boolean mThumbRotatable;

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    //属性
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CircleProgress, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.CircleProgress_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.CircleProgress_strokeWidth, 10);
        mCircleColor = typeArray.getColor(R.styleable.CircleProgress_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.CircleProgress_ringColor, 0xFFFFFFFF);
        mRingBgColor = typeArray.getColor(R.styleable.CircleProgress_ringBgColor, 0xFFFFFFFF);
        mThumbDrawable = typeArray.getDrawable(R.styleable.CircleProgress_thumb);
        mThumbRotatable = typeArray.getBoolean(R.styleable.CircleProgress_thumbRotatable,false);

        mThumbHeight = typeArray.getDimension(R.styleable.CircleProgress_thumbHeight, 50);
        mThumbWith = typeArray.getDimension(R.styleable.CircleProgress_thumbWidth, 50);
        //圆环中心半径
        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    //初始化画笔
    private void initVariable() {
        //内圆
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        //外圆弧背景
        mRingPaintBg = new Paint();
        mRingPaintBg.setAntiAlias(true);
        mRingPaintBg.setColor(mRingBgColor);
        mRingPaintBg.setStyle(Paint.Style.STROKE);
        mRingPaintBg.setStrokeWidth(mStrokeWidth);

        mThumbPaint = new Paint();
        mThumbPaint.setAntiAlias(true);

        //外圆弧
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);
        //mRingPaint.setStrokeCap(Paint.Cap.ROUND);//设置线冒样式，有圆 有方

        //中间字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mRingColor);
        mTextPaint.setTextSize(mRadius / 2);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

        //mThumbBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.rect)).getBitmap();
        if(mThumbDrawable!=null) {
            mThumbBitmap = drawableToBitmap(mThumbDrawable);
            mThumbPoint = new Point();
            mThumbBitmap = zoomImg(mThumbBitmap, (int) mThumbWith, (int) mThumbHeight);
        }
    }

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //画图
    @Override
    protected void onDraw(Canvas canvas) {
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        // 进度换算角度
        float angle = ((float) mProgress / mTotalProgress) * 360;
        double angleR = (angle / 180.0f) * Math.PI;

        // 如果需要绘制按钮
        if(mThumbBitmap!=null) {
            // 首次进入初始化按钮的坐标
            if (mFirst) {
                mThumbPoint.x = getWidth() / 2;
                mThumbPoint.y = mYCenter + (int) mRingRadius;
                mFirst = false;
                mLastAngleR = angleR;
            } else {
                double daltaAngleR = mLastAngleR - angleR;
                double mLastAngleR = angleR;
                // update the location of thumb.
                //   mThumbPoint.x=(int)((mThumbPoint.x-mXCenter)*Math.cos(daltaAngleR)-(mThumbPoint.y-mYCenter)*Math.sin(daltaAngleR)+mXCenter);
                //  mThumbPoint.y=(int)((mThumbPoint.y-mYCenter)*Math.cos(daltaAngleR)-(mThumbPoint.x-mXCenter)*Math.sin(daltaAngleR)+mYCenter);
                mThumbPoint.x = (int) (mXCenter - mRingRadius * Math.sin(angleR));
                mThumbPoint.y = (int) (mYCenter + mRingRadius * Math.cos(angleR));
            }
        }
        //内圆
        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);

        //外圆弧背景
        RectF oval1 = new RectF();
        oval1.left = (mXCenter - mRingRadius);
        oval1.top = (mYCenter - mRingRadius);
        oval1.right = mRingRadius * 2 + (mXCenter - mRingRadius);
        oval1.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
        canvas.drawArc(oval1, 0, 360, false, mRingPaintBg); //圆弧所在的椭圆对象、圆弧的起始角度、圆弧的角度、是否显示半径连线

        //外圆弧
        if (mProgress > 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            canvas.drawArc(oval, 90, angle, false, mRingPaint); //

            //字体
            String txt = mProgress + "分";
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);

            if(mThumbBitmap!=null){
                if(mThumbRotatable){
                    drawRotateBitmap(canvas, mThumbPaint, mThumbBitmap, angle, mThumbPoint.x - mThumbWith / 2, mThumbPoint.y - mThumbHeight / 2);
                }else{
                    canvas.drawBitmap(mThumbBitmap,mThumbPoint.x - mThumbWith / 2,mThumbPoint.y - mThumbHeight / 2,mThumbPaint);
                }
            }
            // canvas.drawBitmap(mThumbBitmap,m,mThumbPaint);

            // canvas.drawBitmap();
        }
    }

    public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }


    /**
     * 绘制自旋转位图
     *
     * @param canvas
     * @param paint
     * @param bitmap   位图对象
     * @param rotation 旋转度数
     * @param posX     在canvas的位置坐标
     * @param posY
     */
    private void drawRotateBitmap(Canvas canvas, Paint paint, Bitmap bitmap,
                                  float rotation, float posX, float posY) {
        Matrix matrix = new Matrix();
        int offsetX = bitmap.getWidth() / 2;
        int offsetY = bitmap.getHeight() / 2;
        matrix.postTranslate(-offsetX, -offsetY);
        matrix.postRotate(rotation);
        matrix.postTranslate(posX + offsetX, posY + offsetY);
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    //设置进度
    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();//重绘
    }
}