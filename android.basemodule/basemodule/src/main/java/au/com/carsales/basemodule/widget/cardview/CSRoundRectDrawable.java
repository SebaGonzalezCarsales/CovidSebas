package au.com.carsales.basemodule.widget.cardview;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.RectF;

public class CSRoundRectDrawable extends RoundRectDrawable {
    public static final int FLAG_LEFT_TOP_CORNER = 0x00000001;
    public static final int FLAG_RIGHT_TOP_CORNER = 0x00000002;
    public static final int FLAG_LEFT_BOTTOM_CORNER = 0x00000004;
    public static final int FLAG_RIGHT_BOTTOM_CORNER = 0x00000008;
    private boolean leftTopRect = false;
    private boolean rightTopRect = false;
    private boolean leftBottomRect = false;
    private boolean rightBottomRect = false;
    CSRoundRectDrawable(ColorStateList backgroundColor, float radius, int cornerFlag) {
        super(backgroundColor, radius);
        this.leftTopRect = (FLAG_LEFT_TOP_CORNER & cornerFlag) == 0;
        this.rightTopRect = (FLAG_RIGHT_TOP_CORNER & cornerFlag) == 0;
        this.leftBottomRect = (FLAG_LEFT_BOTTOM_CORNER & cornerFlag) == 0;
        this.rightBottomRect = (FLAG_RIGHT_BOTTOM_CORNER & cornerFlag) == 0;
    }

    CSRoundRectDrawable(ColorStateList backgroundColor, float radius) {
        super(backgroundColor, radius);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (leftTopRect) {
            canvas.drawRect(buildLeftTopRect(), mPaint);
        }

        if (rightTopRect) {
            canvas.drawRect(buildRightTopRect(), mPaint);
        }

        if (rightBottomRect) {
            canvas.drawRect(buildRightBottomRect(), mPaint);
        }

        if (leftBottomRect) {
            canvas.drawRect(buildLeftBottomRect(), mPaint);
        }
    }

    @Override
    public void getOutline(Outline outline) {
        if (buildConvexPath().isConvex()) {
            outline.setConvexPath(buildConvexPath());
        } else {
            super.getOutline(outline);
        }

    }
    protected Path buildConvexPath() {
        Path path = new Path();

        path.moveTo(mBoundsF.left +1.0f, (mBoundsF.top + mBoundsF.bottom) / 2.0f);
        path.lineTo(mBoundsF.left +1.0f, mBoundsF.top + mRadius);
        if (leftTopRect) {
            path.lineTo(mBoundsF.left+1.0f, mBoundsF.top);
        } else {
            RectF rectF = new RectF(mBoundsF.left+1.0f, mBoundsF.top, mBoundsF.left + mRadius * 2.0f, mBoundsF.top + mRadius * 2.0f);
            path.arcTo(rectF, 180.0f, 90.0f);
        }

        path.lineTo(mBoundsF.right - mRadius, mBoundsF.top);
        if (rightTopRect) {
            path.lineTo(mBoundsF.right, mBoundsF.top);
        } else {
            RectF rectF = new RectF(mBoundsF.right - mRadius * 2.0f, mBoundsF.top, mBoundsF.right, mBoundsF.top + mRadius * 2.0f);
            path.arcTo(rectF, 270.0f, 90.0f);
        }

        path.lineTo(mBoundsF.right, mBoundsF.bottom - mRadius);
        if (rightBottomRect) {
            path.lineTo(mBoundsF.right, mBoundsF.bottom);
        } else {
            RectF rectF = new RectF(mBoundsF.right - mRadius * 2.0f, mBoundsF.bottom - mRadius * 2.0f, mBoundsF.right, mBoundsF.bottom);
            path.arcTo(rectF, 0.0f, 90.0f);
        }

        path.lineTo(mBoundsF.left+1.0f + mRadius, mBoundsF.bottom);
        if (leftBottomRect) {
            path.lineTo(mBoundsF.left+1.0f, mBoundsF.bottom);
        } else {
            RectF rectF = new RectF(mBoundsF.left+1.0f, mBoundsF.bottom - mRadius * 2.0f, mBoundsF.left + mRadius * 2.0f, mBoundsF.bottom);
            path.arcTo(rectF, 90.0f, 90.0f);
        }

        path.close();
        return path;
    }


    private RectF buildLeftTopRect() {
        RectF rectF = new RectF();
        rectF.left = mBoundsF.left;
        rectF.top = mBoundsF.top;
        rectF.right = mBoundsF.left + mRadius * 2.0f;
        rectF.bottom = mBoundsF.top + mRadius * 2.0f;

        return rectF;
    }

    private RectF buildRightTopRect() {
        RectF rectF = new RectF();
        rectF.left = mBoundsF.right - mRadius * 2.0f;
        rectF.top = mBoundsF.top;
        rectF.right = mBoundsF.right;
        rectF.bottom = mBoundsF.top + mRadius * 2.0f;

        return rectF;
    }

    private RectF buildRightBottomRect() {
        RectF rectF = new RectF();
        rectF.left = mBoundsF.right - mRadius * 2.0f;
        rectF.top = mBoundsF.bottom - mRadius * 2.0f;
        rectF.right = mBoundsF.right;
        rectF.bottom = mBoundsF.bottom;

        return rectF;
    }

    private RectF buildLeftBottomRect() {
        RectF rectF = new RectF();
        rectF.left = mBoundsF.left;
        rectF.top = mBoundsF.bottom - mRadius * 2.0f;
        rectF.right = mBoundsF.left + mRadius * 2.0f;
        rectF.bottom = mBoundsF.bottom;

        return rectF;
    }

    public void showCorner(boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom){
        this.leftTopRect = !leftTop;
        this.rightTopRect = !rightTop;
        this.leftBottomRect = !leftBottom;
        this.rightBottomRect = !rightBottom;
        invalidateSelf();
    }

    public void showLeftTopRect(boolean show) {
        this.leftTopRect = show;
        invalidateSelf();
    }

    public void showRightTopRect(boolean show) {
        this.rightTopRect = show;
        invalidateSelf();
    }

    public void showRightBottomRect(boolean show) {
        this.rightBottomRect = show;
        invalidateSelf();
    }

    public void showLeftBottomRect(boolean show) {
        this.leftBottomRect = show;
        invalidateSelf();
    }
}
