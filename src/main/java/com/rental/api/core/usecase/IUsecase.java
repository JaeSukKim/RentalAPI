package com.rental.api.core.usecase;

public interface IUsecase <I extends IUsecase.ICommand, O extends IUsecase.IResult> {

    O execute(final I input);

    interface ICommand{}

    interface IResult{}
}
