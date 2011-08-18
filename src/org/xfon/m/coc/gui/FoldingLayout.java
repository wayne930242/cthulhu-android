package org.xfon.m.coc.gui;

import org.xfon.m.coc.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FoldingLayout extends LinearLayout implements OnClickListener {	
	private Button btnSwitch;
	
	public FoldingLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public FoldingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.folding_layout, this, true);
        if ( !isEnabled() ) setEnabled( true );
        
        btnSwitch = (Button)this.findViewById( R.id.btnSwitch );        
        btnSwitch.setOnClickListener( this );
        
        TypedArray a = context.obtainStyledAttributes( attrs, R.styleable.FoldingLayout );
        boolean collapsed = a.getBoolean( R.styleable.FoldingLayout_isCollapsed, false );
        if ( collapsed ) collapse();
	}

	public void setTitle( String title ) {
		TextView tv = (TextView)this.findViewById( R.id.title );		
		tv.setText( title );
	}
	
	@Override
	public void onClick(View v) {
		if ( v != btnSwitch ) return;
		ViewGroup vg = (ViewGroup)this.findViewById( R.id.mainPanel );
		if ( vg.getVisibility() ==  View.VISIBLE ) {
			collapse();
		}
		else {
			expand();
		}
	}	
	
	private void expand() {
		ViewGroup vg = (ViewGroup)this.findViewById( R.id.mainPanel );
		vg.setVisibility( View.VISIBLE );
		btnSwitch.setBackgroundResource( R.drawable.up );			
	}

	private void collapse() {
		ViewGroup vg = (ViewGroup)this.findViewById( R.id.mainPanel );
		vg.setVisibility( View.GONE );
		btnSwitch.setBackgroundResource( R.drawable.down );
	}
}
