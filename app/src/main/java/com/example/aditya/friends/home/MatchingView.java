package com.example.aditya.friends.home;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;

import com.example.aditya.friends.R;

import java.util.Random;

public class MatchingView extends ViewGroup {

    public static final int SWIPE_DIRECTION_BOTH = 0;
    public static final int SWIPE_DIRECTION_ONLY_LEFT = 1;
    public static final int SWIPE_DIRECTION_ONLY_RIGHT = 2;

    public static final int DEFAULT_ANIMATION_DURATION = 300;
    public static final int DEFAULT_STACK_SIZE = 3;
    public static final int DEFAULT_STACK_ROTATION = 8;
    public static final float DEFAULT_SWIPE_ROTATION = 30f;
    public static final float DEFAULT_SWIPE_OPACITY = 1f;
    public static final float DEFAULT_SCALE_FACTOR = 1f;
    public static final boolean DEFAULT_DISABLE_HW_ACCELERATION = true;

    private static final String KEY_SUPER_STATE = "superState";
    private static final String KEY_CURRENT_INDEX = "currentIndex";

    private Adapter mAdapter;
    private Random mRandom;

    private int mAllowedSwipeDirections;
    private int mAnimationDuration;
    private int mCurrentViewIndex;
    private int mNumberOfStackedViews;
    private int mViewSpacing;
    private int mViewRotation;
    private float mSwipeRotation;
    private float mSwipeOpacity;
    private float mScaleFactor;
    private boolean mDisableHwAcceleration;
    private boolean mIsFirstLayout = true;

    private View mTopView;
    private MatchingViewHelper mMatchingViewHelper;
    private DataSetObserver mDataObserver;
    private SwipeMatchListener mListener;
    private SwipeProgressListener mProgressListener;

    public MatchingView(Context context) {
        super(context, null);
        initialize();
    }

    public MatchingView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initialize();
    }

    private void initialize() {
        mRandom = new Random();

        setClipToPadding(false);
        setClipChildren(false);

        mMatchingViewHelper = new MatchingViewHelper(this);
        mMatchingViewHelper.setAnimationDuration(mAnimationDuration);
        mMatchingViewHelper.setRotation(mSwipeRotation);
        mMatchingViewHelper.setOpacityEnd(mSwipeOpacity);

        mDataObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                invalidate();
                requestLayout();
            }
        };

        mAllowedSwipeDirections = SWIPE_DIRECTION_BOTH;
        mAnimationDuration = DEFAULT_ANIMATION_DURATION;
        mNumberOfStackedViews = DEFAULT_STACK_SIZE;
        mViewSpacing = 12;
        mViewRotation = 0; //DEFAULT_STACK_ROTATION;
        mSwipeRotation = DEFAULT_SWIPE_ROTATION;
        mSwipeOpacity = DEFAULT_SWIPE_OPACITY;
        mScaleFactor = DEFAULT_SCALE_FACTOR;
        mDisableHwAcceleration = DEFAULT_DISABLE_HW_ACCELERATION;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(KEY_CURRENT_INDEX, mCurrentViewIndex - getChildCount());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mCurrentViewIndex = bundle.getInt(KEY_CURRENT_INDEX);
            state = bundle.getParcelable(KEY_SUPER_STATE);
        }

        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (mAdapter == null || mAdapter.isEmpty()) {
            mCurrentViewIndex = 0;
            removeAllViewsInLayout();
            return;
        }

        for (int x = getChildCount(); x < mNumberOfStackedViews && mCurrentViewIndex < mAdapter.getCount(); x++) {
            addNextView();
        }

        reorderItems();

        mIsFirstLayout = false;
    }

    private void addNextView() {
        if (mCurrentViewIndex < mAdapter.getCount()) {
            View bottomView = mAdapter.getView(mCurrentViewIndex, null, this);
            bottomView.setTag(R.id.new_view, true);

            if (!mDisableHwAcceleration) {
                bottomView.setLayerType(LAYER_TYPE_HARDWARE, null);
            }

            if (mViewRotation > 0) {
                bottomView.setRotation(mRandom.nextInt(mViewRotation) - (mViewRotation / 2));
            }

            int width = getWidth() - (getPaddingLeft() + getPaddingRight());
            int height = getHeight() - (getPaddingTop() + getPaddingBottom());

            LayoutParams params = bottomView.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
            }

            int measureSpecWidth = MeasureSpec.AT_MOST;
            int measureSpecHeight = MeasureSpec.AT_MOST;

            if (params.width == LayoutParams.MATCH_PARENT) {
                measureSpecWidth = MeasureSpec.EXACTLY;
            }

            if (params.height == LayoutParams.MATCH_PARENT) {
                measureSpecHeight = MeasureSpec.EXACTLY;
            }

            bottomView.measure(measureSpecWidth | width, measureSpecHeight | height);
            addViewInLayout(bottomView, 0, params, true);

            mCurrentViewIndex++;
        }
    }

    private void reorderItems() {
        for (int x = 0; x < getChildCount(); x++) {
            View childView = getChildAt(x);
            int topViewIndex = getChildCount() - 1;

            int distanceToViewAbove = (topViewIndex * mViewSpacing) - (x * mViewSpacing);
            int newPositionX = (getWidth() - childView.getMeasuredWidth()) / 2;
            int newPositionY = distanceToViewAbove + getPaddingTop();

            childView.layout(
                    newPositionX,
                    getPaddingTop(),
                    newPositionX + childView.getMeasuredWidth(),
                    getPaddingTop() + childView.getMeasuredHeight());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                childView.setTranslationZ(x);
            }

            boolean isNewView = (boolean) childView.getTag(R.id.new_view);
            float scaleFactor = (float) Math.pow(mScaleFactor, getChildCount() - x);

            if (x == topViewIndex) {
                mMatchingViewHelper.unregisterObservedView();
                mTopView = childView;
                mMatchingViewHelper.registerObservedView(mTopView, newPositionX, newPositionY);
            }

            if (!mIsFirstLayout) {

                if (isNewView) {
                    childView.setTag(R.id.new_view, false);
                    childView.setAlpha(0);
                    childView.setY(newPositionY);
                    childView.setScaleY(scaleFactor);
                    childView.setScaleX(scaleFactor);
                }

                childView.animate()
                        .y(newPositionY)
                        .scaleX(scaleFactor)
                        .scaleY(scaleFactor)
                        .alpha(1)
                        .setDuration(mAnimationDuration);

            } else {
                childView.setTag(R.id.new_view, false);
                childView.setY(newPositionY);
                childView.setScaleY(scaleFactor);
                childView.setScaleX(scaleFactor);
            }
        }
    }

    private void removeTopView() {
        if (mTopView != null) {
            removeView(mTopView);
            mTopView = null;
        }

        if (getChildCount() == 0) {
            if (mListener != null) mListener.onStackEmpty();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void onSwipeStart() {
        if (mProgressListener != null) mProgressListener.onSwipeStart(getCurrentPosition());
    }

    public void onSwipeProgress(float progress) {
        if (mProgressListener != null)
            mProgressListener.onSwipeProgress(getCurrentPosition(), progress);
    }

    public void onSwipeEnd() {
        if (mProgressListener != null) mProgressListener.onSwipeEnd(getCurrentPosition());
    }

    public void onViewSwipedToLeft() {
        if (mListener != null) mListener.onViewSwipedToLeft(getCurrentPosition());
        removeTopView();
    }

    public void onViewSwipedToRight() {
        if (mListener != null) mListener.onViewSwipedToRight(getCurrentPosition());
        removeTopView();
    }

    public int getCurrentPosition() {
        return mCurrentViewIndex - getChildCount();
    }

    public Adapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(Adapter adapter) {
        if (mAdapter != null) mAdapter.unregisterDataSetObserver(mDataObserver);
        mAdapter = adapter;
        mAdapter.registerDataSetObserver(mDataObserver);
    }

    public int getAllowedSwipeDirections() {
        return mAllowedSwipeDirections;
    }

    public void setAllowedSwipeDirections(int directions) {
        mAllowedSwipeDirections = directions;
    }

    public void setListener(@Nullable SwipeMatchListener listener) {
        mListener = listener;
    }

    public void setSwipeProgressListener(@Nullable SwipeProgressListener listener) {
        mProgressListener = listener;
    }

    public View getTopView() {
        return mTopView;
    }

    public void swipeTopViewToRight() {
        if (getChildCount() == 0) return;
        mMatchingViewHelper.swipeViewToRight();
    }

    public void swipeTopViewToLeft() {
        if (getChildCount() == 0) return;
        mMatchingViewHelper.swipeViewToLeft();
    }

    public void resetStack() {
        mCurrentViewIndex = 0;
        removeAllViewsInLayout();
        requestLayout();
    }

    public interface SwipeMatchListener {
        void onViewSwipedToLeft(int position);
        void onViewSwipedToRight(int position);
        void onStackEmpty();
    }

    public interface SwipeProgressListener {
        void onSwipeStart(int position);
        void onSwipeProgress(int position, float progress);
        void onSwipeEnd(int position);
    }
}

