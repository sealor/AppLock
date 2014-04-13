(define (script-fu-app-lock-icon)
	(let* (
			(theWidth 512)
			(theHeight 512)
			(theImage (car (gimp-image-new theWidth theHeight RGB)))
			(theShieldVector (create-shield-vector theImage))
		)
		(create-shield-area-layer theImage theWidth theHeight theShieldVector)
		(create-shield-frame-layer theImage theWidth theHeight theShieldVector)
		(create-lock-bow theImage theWidth theHeight)
		(create-lock-body theImage theWidth theHeight)

		(gimp-selection-none theImage)
		(gimp-display-new theImage)
		(gimp-image-clean-all theImage)
	)
)

(define (create-shield-vector theImage)
	(let* (
			(theVector (car (gimp-vectors-new theImage "shield vector")))
			(theStroke (car (gimp-vectors-bezier-stroke-new-moveto theVector 255 40)))
		)
		(gimp-image-insert-vectors theImage theVector 0 -1)

		(gimp-vectors-bezier-stroke-conicto theVector theStroke 160 115 40 50)
		(gimp-vectors-bezier-stroke-conicto theVector theStroke 60 430 255 470)
		(gimp-vectors-bezier-stroke-conicto theVector theStroke (- 512 60) 430 (- 512 40) 50)
		(gimp-vectors-bezier-stroke-conicto theVector theStroke (- 512 160) 115 255 40)

		theVector
	)
)

(define (create-shield-area-layer theImage theWidth theHeight theShieldVector)
	(let* (
			(theLayer (car (gimp-layer-new theImage theWidth theHeight RGBA-IMAGE "shield area layer" 100 NORMAL)))
		)
		(gimp-image-add-layer theImage theLayer 0)
		(gimp-image-select-item theImage CHANNEL-OP-REPLACE theShieldVector)

		(gimp-context-set-gradient "Flare Sizefac 101")
		(gimp-edit-blend
			theLayer
			CUSTOM-MODE
			NORMAL-MODE
			GRADIENT-LINEAR
			100								;opacity
			0									;offset
			REPEAT-NONE
			TRUE							;reverse gradient
			FALSE							;supersampling
			1									;supersampling max-depth
			0									;supersampling threshold
			TRUE							;dither
			200								;x1
			80								;y1
			-250							;x2
			400								;y2
		)
	)
)

(define (create-shield-frame-layer theImage theWidth theHeight theShieldVector)
	(let* (
			(theLayer (car (gimp-layer-new theImage theWidth theHeight RGBA-IMAGE "shield frame layer" 100 NORMAL)))
		)
		(gimp-image-add-layer theImage theLayer 0)
		(gimp-image-select-item theImage CHANNEL-OP-REPLACE theShieldVector)

		(gimp-selection-border theImage 60)
		(gimp-selection-sharpen theImage)
		(gimp-selection-feather theImage 3)

		(gimp-context-set-gradient "Caribbean Blues")
		(gimp-edit-blend
			theLayer
			CUSTOM-MODE
			NORMAL-MODE
			GRADIENT-LINEAR
			100								;opacity
			0									;offset
			REPEAT-NONE
			TRUE							;reverse gradient
			FALSE							;supersampling
			1									;supersampling max-depth
			0									;supersampling threshold
			TRUE							;dither
			theWidth					;x1
			0									;y1
			0									;x2
			theHeight					;y2
		)

		(script-fu-drop-shadow theImage theLayer 0 0 10 '(0 0 0) 90 0)
	)
)

(define (create-lock-bow theImage theWidth theHeight)
	(let* (
			(theLayer (car (gimp-layer-new theImage theWidth theHeight RGBA-IMAGE "lock bow layer" 100 NORMAL)))

			(theOuterHeight 210)
			(theOuterWidth 210)
			(theOuterX (- (/ theWidth 2) (/ theOuterWidth 2)))
			(theOuterY (- (/ theHeight 2) (/ theOuterHeight 2)))
			(theInnerHeight 120)
			(theInnerWidth 120)
			(theInnerX (- (/ theWidth 2) (/ theInnerWidth 2)))
			(theInnerY (- (/ theHeight 2) (/ theInnerHeight 2)))
		)
		(gimp-image-add-layer theImage theLayer 0)

		(gimp-ellipse-select theImage
			theOuterX
			theOuterY
			theOuterWidth
			theOuterHeight
			CHANNEL-OP-REPLACE
			FALSE
			FALSE
			0
		)

		(gimp-ellipse-select theImage
			theInnerX
			theInnerY
			theInnerWidth
			theInnerHeight
			CHANNEL-OP-SUBTRACT
			FALSE
			FALSE
			0
		)

		(gimp-rect-select theImage
			0
			(/ theHeight 2)
			theWidth
			theHeight
			CHANNEL-OP-SUBTRACT
			FALSE
			0
		)

		(gimp-rect-select theImage
			theOuterX
			(+ theOuterY (/ theOuterHeight 2))
			(- theInnerX theOuterX)
			100
			CHANNEL-OP-ADD
			FALSE
			0
		)

		(gimp-rect-select theImage
			(+ theOuterX (- theOuterWidth (- theInnerX theOuterX)))
			(+ theOuterY (/ theOuterHeight 2))
			(- theInnerX theOuterX)
			100
			CHANNEL-OP-ADD
			FALSE
			0
		)

		(gimp-selection-translate theImage 70 -15)
		(gimp-selection-feather theImage 3)

		(gimp-context-set-gradient "Rounded edge")
		(gimp-edit-blend
			theLayer
			CUSTOM-MODE
			NORMAL-MODE
			GRADIENT-LINEAR
			100								;opacity
			0									;offset
			REPEAT-NONE
			TRUE							;reverse gradient
			FALSE							;supersampling
			1									;supersampling max-depth
			0									;supersampling threshold
			TRUE							;dither
			(+ theOuterX theOuterWidth 85)			;x1
			theOuterY														;y1
			(+ theOuterX 85)										;x2
			(+ theOuterY theOuterHeight)				;y2
		)

		(script-fu-drop-shadow theImage theLayer 5 5 25 '(0 0 0) 80 0)
	)
)

(define (create-lock-body theImage theWidth theHeight)
	(let* (
			(theLayer (car (gimp-layer-new theImage theWidth theHeight RGBA-IMAGE "lock body layer" 100 NORMAL)))

			(theLockHeight 198)
			(theLockWidth 294)
			(theLockX (- (/ theWidth 2) (/ theLockWidth 2)))
			(theLockY (- (/ theHeight 2) (/ theLockHeight 2)))
			(theLockCircleHeight 32)
			(theLockCircleWidth 32)
			(theLockCircleX (- (/ theWidth 2) (/ theLockCircleWidth 2)))
			(theLockCircleY (- (/ theHeight 2) (/ theLockCircleHeight 2)))
			(theLockCircleRectHeight 25)
			(theLockCircleRectWidth 14)
			(theLockCircleRectX (- (/ theWidth 2) (/ theLockCircleRectWidth 2)))
			(theLockCircleRectY (- (/ theHeight 2) (/ theLockCircleRectHeight 2)))
		)
		(gimp-image-add-layer theImage theLayer 0)

		(gimp-image-select-round-rectangle theImage CHANNEL-OP-REPLACE
			theLockX theLockY theLockWidth theLockHeight 9 9)

		(gimp-image-select-round-rectangle theImage CHANNEL-OP-SUBTRACT
			theLockCircleRectX (+ 28 theLockCircleRectY) theLockCircleRectWidth theLockCircleRectHeight 3 3)

		(gimp-ellipse-select theImage
			theLockCircleX
			theLockCircleY
			theLockCircleWidth
			theLockCircleHeight
			CHANNEL-OP-SUBTRACT
			FALSE
			FALSE
			0
		)

		(gimp-selection-translate theImage 70 120)
		(gimp-selection-feather theImage 3)

		(gimp-context-set-gradient "Golden")
		(gimp-edit-blend
			theLayer
			CUSTOM-MODE
			NORMAL-MODE
			GRADIENT-LINEAR
			100								;opacity
			0									;offset
			REPEAT-NONE
			TRUE							;reverse gradient
			FALSE							;supersampling
			1									;supersampling max-depth
			0									;supersampling threshold
			TRUE							;dither
			(* theWidth 0.75)			;x1
			(* theHeight 0.25)		;y1
			(* theHeight 0.25)		;x2
			(* theWidth 0.75)			;y2
		)

		(gimp-brightness-contrast theLayer 10 10)
		(script-fu-drop-shadow theImage theLayer 5 5 25 '(0 0 0) 80 0)
	)
)

(script-fu-register
	"script-fu-app-lock-icon"			;function name
	"AppLock icon script"					;menu label
	"Create the icon for AppLock"	;description
	"Stefan Richter"							;author
	"CC"													;copyright notice
	"April 06, 2014"							;create date
	""														;image type
)

(script-fu-menu-register
	"script-fu-app-lock-icon"
	"<Image>/File/Create"
)
