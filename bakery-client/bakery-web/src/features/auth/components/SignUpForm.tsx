import { toast } from 'sonner';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { cn } from '@/lib/utils';
import { Button } from '@ui/ui/button';
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from '@ui/ui/form';
import { Input } from '@ui/ui/input';
import { format } from 'date-fns';
import { Popover, PopoverContent, PopoverTrigger } from '@ui/ui/popover';
import { Calendar } from '@ui/ui/calendar';
import { Calendar as CalendarIcon, Eye, EyeClosed } from 'lucide-react';
import { PhoneInput } from '@ui/inputs/PhoneInput';
import { Separator } from '@radix-ui/react-dropdown-menu';
import type { APIResponse, AuthResponse, SignUpRequest } from '@/env';
import { isValidPhoneNumber } from 'react-phone-number-input';
import { useState } from 'react';

const formSchema = z.object({
  firstNameField: z
    .string()
    .min(2, { error: 'First name must be longer than 2 characters' })
    .max(255, { error: 'First name must not be longer than 255 characters' }),
  lastNameField: z
    .string()
    .min(2, { error: 'Last name must be longer than 2 characters' })
    .max(255, { error: 'Last name must not be longer than 255 characters' }),
  emailField: z.email(),
  phoneNumberField: z
    .string()
    .refine(isValidPhoneNumber, { message: 'Phone number is not valid' }),
  birthDateField: z.date(),
  usernameField: z
    .string()
    .min(4, { error: 'Username must be longer than 4 characters' })
    .max(255, { error: 'Usernam must not be longer than 255 characters' })
    .optional(),
  passwordField: z
    .string()
    .min(4, { error: 'Password must be longer than 4 characters' })
    .max(255, { error: 'Password must not be longer than 255 characters' }),
  confirmPasswordField: z
    .string()
    .min(4, { error: 'Password must be longer than 4 characters' })
    .max(255, { error: 'Password must not be longer than 255 characters' }),
});

type Schema = z.infer<typeof formSchema>;

export default function MyForm() {
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const form = useForm<Schema>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      firstNameField: '',
      lastNameField: '',
      emailField: '',
      phoneNumberField: '',
      birthDateField: new Date(),
      usernameField: '',
      passwordField: '',
      confirmPasswordField: '',
    },
  });

  const togglePassword = (
    value: boolean,
    setValue: React.Dispatch<React.SetStateAction<boolean>>
  ) => {
    setValue(!value);
  };

  const onSubmit = async (values: z.infer<typeof formSchema>) => {
    try {
      if (values.passwordField !== values.confirmPasswordField) {
        form.setError('confirmPasswordField', {
          message: "The passwords don't match",
        });
        return;
      }

      const birthDate = values.birthDateField;

      const signUpDto: SignUpRequest = {
        first_name: values.firstNameField,
        last_name: values.lastNameField,
        phone_number: values.phoneNumberField,
        birth_date: Date.UTC(
          birthDate.getUTCFullYear(),
          birthDate.getUTCMonth(),
          birthDate.getUTCDate(),
          birthDate.getUTCHours(),
          birthDate.getUTCMinutes(),
          birthDate.getUTCSeconds()
        ),
        email: values.emailField,
        username: values.usernameField ?? '',
        password: values.passwordField,
      };
      const req = await fetch('/api/auth/signup', {
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(signUpDto),
        method: 'POST',
      });
      if (!req.ok) {
        const res: APIResponse<AuthResponse> = await req.json();
        toast.error(res.message);
        return;
      }

      toast.success('Welcome!');
      window.open('/', '_self');
    } catch (error) {
      console.error('Form submission error', error);
      toast.error('Failed to submit the form. Please try again.');
    }
  };

  return (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="flex flex-col gap-4"
      >
        <div className="grid gap-4 sm:grid-cols-12">
          <div className="col-span-6">
            <FormField
              control={form.control}
              name="firstNameField"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>First name</FormLabel>
                  <FormControl>
                    <Input placeholder="Name..." type="text" {...field} />
                  </FormControl>
                  <FormDescription>Type your first name.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <div className="col-span-6">
            <FormField
              control={form.control}
              name="lastNameField"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Last name</FormLabel>
                  <FormControl>
                    <Input placeholder="Name..." type="text" {...field} />
                  </FormControl>
                  <FormDescription>Type your last name.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
        </div>

        <div className="grid gap-4 sm:grid-cols-12">
          <div className="col-span-6">
            <FormField
              control={form.control}
              name="phoneNumberField"
              render={({ field }) => (
                <FormItem className="flex flex-col items-start">
                  <FormLabel>Phone number</FormLabel>
                  <FormControl className="w-full">
                    <PhoneInput
                      placeholder="Number..."
                      {...field}
                      defaultCountry="VE"
                    />
                  </FormControl>
                  <FormDescription>Type your phone number.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <div className="col-span-6">
            <FormField
              control={form.control}
              name="birthDateField"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <FormLabel>Date of birth</FormLabel>
                  <Popover>
                    <PopoverTrigger asChild>
                      <FormControl>
                        <Button
                          variant={'outline'}
                          className={cn(
                            'w-auto pl-3 text-left font-normal',
                            !field.value && 'text-muted-foreground'
                          )}
                        >
                          {field.value ? (
                            format(field.value, 'PPP')
                          ) : (
                            <span>Pick a date</span>
                          )}
                          <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                        </Button>
                      </FormControl>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0" align="start">
                      <Calendar
                        mode="single"
                        selected={field.value}
                        onSelect={field.onChange}
                        captionLayout="dropdown"
                        autoFocus
                      />
                    </PopoverContent>
                  </Popover>
                  <FormDescription>Enter your date of birth.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
        </div>

        <FormField
          control={form.control}
          name="emailField"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input placeholder="Email..." type="email" {...field} />
              </FormControl>
              <FormDescription>Type here your email.</FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="usernameField"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Username</FormLabel>
              <FormControl>
                <Input placeholder="Username..." type="text" {...field} />
              </FormControl>
              <FormDescription>
                Type here your username, if empty your email will be used
                instead.
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />

        <div className="grid gap-4 sm:grid-cols-12">
          <div className="col-span-6">
            <FormField
              control={form.control}
              name="passwordField"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <div className="flex items-center justify-between">
                      <Input
                        placeholder="Password..."
                        type={showPassword ? 'text' : 'password'}
                        {...field}
                      />
                      <Button
                        onClick={() => {
                          togglePassword(showPassword, setShowPassword);
                        }}
                        type="button"
                        variant="ghost"
                        size="icon"
                      >
                        {showPassword ? <EyeClosed /> : <Eye />}
                      </Button>
                    </div>
                  </FormControl>
                  <FormDescription>Type your password.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>

          <div className="col-span-6">
            <FormField
              control={form.control}
              name="confirmPasswordField"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Confirm password</FormLabel>
                  <FormControl>
                    <div className="flex items-center justify-between">
                      <Input
                        placeholder="Password..."
                        type={showConfirmPassword ? 'text' : 'password'}
                        {...field}
                      />
                      <Button
                        onClick={() => {
                          togglePassword(
                            showConfirmPassword,
                            setShowConfirmPassword
                          );
                        }}
                        type="button"
                        variant="ghost"
                        size="icon"
                      >
                        {showConfirmPassword ? <EyeClosed /> : <Eye />}
                      </Button>
                    </div>
                  </FormControl>
                  <FormDescription>Type your password again.</FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
          </div>
        </div>

        <Separator />

        <Button type="submit">Submit</Button>
      </form>
    </Form>
  );
}
