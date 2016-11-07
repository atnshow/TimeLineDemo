package com.ec.module.timelinemodule;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HP-HP on 05-12-2015.
 */
public class TimelineView extends View {

    private Drawable mMarker;
    private Drawable mStartLine;
    private Drawable mEndLine;
    private int mMarkerSize;
    private int mLineSize;
    private int mLineOrientation;
    private boolean mMarkerInCenter;

    private int mLineAMarkerMargin;

    private Rect mBounds;
    private Context mContext;
    //
    private boolean mShowStartLine = true;
    private boolean mShowEndLine = true;

    public TimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.timeline_style);
        mMarker = typedArray.getDrawable(R.styleable.timeline_style_timeline_marker);
        mStartLine = typedArray.getDrawable(R.styleable.timeline_style_timeline_line);
        mEndLine = typedArray.getDrawable(R.styleable.timeline_style_timeline_line);
        mMarkerSize = typedArray.getDimensionPixelSize(R.styleable.timeline_style_timeline_marker_size, 25);
        mLineSize = typedArray.getDimensionPixelSize(R.styleable.timeline_style_timeline_line_size, 2);
        mLineOrientation = typedArray.getInt(R.styleable.timeline_style_timeline_line_orientation, 1);
        mMarkerInCenter = typedArray.getBoolean(R.styleable.timeline_style_timeline_marker_in_center, true);
        mLineAMarkerMargin = typedArray.getDimensionPixelSize(R.styleable.timeline_style_timeline_line_marker_margin, 0);
        typedArray.recycle();

        if (mMarker == null) {
            if (Build.VERSION.SDK_INT >= 21) {
                mMarker = mContext.getResources().getDrawable(R.drawable.timeline_marker, null);
            } else {
                mMarker = mContext.getResources().getDrawable(R.drawable.timeline_marker);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Width measurements of the width and height and the inside view of child controls
        int w = mMarkerSize + getPaddingLeft() + getPaddingRight();
        int h = mMarkerSize + getPaddingTop() + getPaddingBottom();

        // Width and height to determine the final view through a systematic approach to decision-making
        int widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
        int heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // When the view is displayed when the callback
        // Positioning Drawable coordinates, then draw
        initDrawable();
    }

    private void initDrawable() {
        int pLeft = getPaddingLeft();
        int pRight = getPaddingRight();
        int pTop = getPaddingTop();
        int pBottom = getPaddingBottom();

        int width = getWidth();// Width of current custom view
        int height = getHeight();

        int cWidth = width - pLeft - pRight;// Circle width
        int cHeight = height - pTop - pBottom;

        int markSize = Math.min(mMarkerSize, Math.min(cWidth, cHeight));

        if (mMarkerInCenter) {
            //Marker in center is true
            if (mMarker != null) {
                mMarker.setBounds((width / 2) - (markSize / 2), (height / 2) - (markSize / 2), (width / 2) + (markSize / 2), (height / 2) + (markSize / 2));
                mBounds = mMarker.getBounds();
            }

        } else {
            //Marker in center us false
            if (mMarker != null) {
                mMarker.setBounds(pLeft, pTop, pLeft + markSize, pTop + markSize);
                mBounds = mMarker.getBounds();
            }
        }

        int centerX = mBounds.centerX();
        int lineLeft = centerX - (mLineSize >> 1);

        if (mLineOrientation == 0) {
            //Horizontal Line
            if (mMarkerInCenter) {
                //在中间
                if (mStartLine != null) {
                    mStartLine.setBounds(0, pTop + (mBounds.height() / 2), mBounds.left - mLineAMarkerMargin, (mBounds.height() / 2) + pTop + mLineSize);
                }

                if (mEndLine != null) {
                    mEndLine.setBounds(mBounds.right + mLineAMarkerMargin, pTop + (mBounds.height() / 2), width, (mBounds.height() / 2) + pTop + mLineSize);
                }
            } else {
                if (mStartLine != null) {
                    mStartLine.setBounds(0, pTop + (mBounds.height() / 2), mBounds.left, (mBounds.height() / 2) + pTop + mLineSize);
                }

                if (mEndLine != null) {
                    mEndLine.setBounds(mBounds.right + mLineAMarkerMargin, pTop + (mBounds.height() / 2), width - mLineAMarkerMargin, (mBounds.height() / 2) + pTop + mLineSize);
                }
            }

        } else {
            //Vertical Line
            if (mMarkerInCenter) {
                //在中间
                if (mStartLine != null) {
                    mStartLine.setBounds(lineLeft, 0, mLineSize + lineLeft, mBounds.top - mLineAMarkerMargin);
                }

                if (mEndLine != null) {
                    mEndLine.setBounds(lineLeft, mBounds.bottom + mLineAMarkerMargin, mLineSize + lineLeft, height);
                }
            } else {
                if (mStartLine != null) {
                    mStartLine.setBounds(lineLeft, 0, mLineSize + lineLeft, mBounds.top);
                }

                if (mEndLine != null) {
                    mEndLine.setBounds(lineLeft, mBounds.bottom + mLineAMarkerMargin, mLineSize + lineLeft, height - mLineAMarkerMargin);
                }
            }

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMarker != null) {
            mMarker.draw(canvas);
        }

        if (mStartLine != null && mShowStartLine) {
            mStartLine.draw(canvas);
        }

        if (mEndLine != null && mShowEndLine) {
            mEndLine.draw(canvas);
        }
    }

    /**
     * 设置 marker
     *
     * @param marker
     */
    public void setMarker(Drawable marker) {
        mMarker = marker;
        initDrawable();
    }

    private void setStartLine(Drawable startLine) {
        mStartLine = startLine;
        initDrawable();
    }

    private void setEndLine(Drawable endLine) {
        mEndLine = endLine;
        initDrawable();
    }

    /**
     * 设置 线
     *
     * @param line
     */
    public void setLine(Drawable line) {
        mStartLine = line;
        mEndLine = line;

        initDrawable();
    }

    /**
     * 设置 marker 的大小
     *
     * @param markerSize
     */
    public void setMarkerSize(int markerSize) {
        mMarkerSize = markerSize;
        initDrawable();
    }

    /**
     * 设置 marker 是否处于中间
     *
     * @param isCenter
     */
    public void setMarkerCenter(boolean isCenter) {
        mMarkerInCenter = isCenter;
        initDrawable();
    }

    /**
     * 设置 线 的大小
     *
     * @param lineSize
     */
    public void setLineSize(int lineSize) {
        mLineSize = lineSize;
        initDrawable();
    }

    /**
     * 设置 线 与 maker 的间隔
     *
     * @param margin
     */
    public void setLineAMarkerMargin(int margin) {
        mLineAMarkerMargin = margin;
        initDrawable();
    }

    /**
     * 根据 类型 画线
     *
     * @param viewType
     */
    public void drawLine(int viewType) {
        if (viewType == TimeLineType.BEGIN) {
            mShowStartLine = false;
            mShowEndLine = true;
        } else if (viewType == TimeLineType.END) {
            mShowStartLine = true;
            mShowEndLine = false;
        } else if (viewType == TimeLineType.ONLY_ONE) {
            mShowStartLine = false;
            mShowEndLine = false;
        } else {
            mShowStartLine = true;
            mShowEndLine = true;
        }

        initDrawable();
        invalidate();
    }

    /**
     * 根据当前 position 和 总共数量 确定 类型
     *
     * @param position
     * @param totalSize
     * @return
     */
    public static int getTimeLineViewType(int position, int totalSize) {
        if (totalSize == 1) {
            return TimeLineType.ONLY_ONE;
        } else if (position == 0) {
            return TimeLineType.BEGIN;
        } else if (position == totalSize - 1) {
            return TimeLineType.END;
        } else {
            return TimeLineType.NORMAL;
        }
    }
}
